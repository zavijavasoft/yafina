package com.zavijavasoft.yafina.ui.operation

import android.content.ClipData
import android.content.Context
import android.os.Build
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.model.ArticleType


data class OperationArticleItem(val id: Long, val title: String, val type: ArticleType, val color: Int)
data class OperationAccountItem(val id: Long, val title: String, val sum: String, val color: Int)

const val INCOME_ARTICLE_RECYCLER_VIEW_TAG = "INCOME_ARTICLE_RECYCLER_VIEW_TAG"
const val OUTCOME_ARTICLE_RECYCLER_VIEW_TAG = "OUTCOME_ARTICLE_RECYCLER_VIEW_TAG"
const val ACCOUNTS_RECYCLER_VIEW_TAG = "ACCOUNTS_RECYCLER_VIEW_TAG"


class ArticleAdapter(var itemsList: List<OperationArticleItem>, val context: Context, val presenter: OperationPresenter)
    : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {


    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnDragListener {

        var id: Long = -1L
        var cardView: CardView = view.findViewById(R.id.thumbnail_article)
        var txtTitle: TextView = view.findViewById(R.id.article_title)
        private var isDragging: Boolean = false


        override fun onDrag(view: View, dragEvent: DragEvent): Boolean {
            val dragAction = dragEvent.action
            val dragView = dragEvent.localState as View

            when (dragAction) {
                DragEvent.ACTION_DRAG_EXITED -> isDragging = false
                DragEvent.ACTION_DRAG_ENTERED -> isDragging = true
                DragEvent.ACTION_DRAG_ENDED -> if (!dragEvent.result) {
                    dragView.visibility = View.VISIBLE
                }
                DragEvent.ACTION_DROP -> if (isDragging) {
                    val articleId = view.tag as Long
                    val accountId = dragView.tag as Long
                    presenter.requireOutcomeTransaction(articleId, accountId)
                    dragView.visibility = View.VISIBLE
                }
            }

            return true
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.thumbnail_article, parent, false)

        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val item = itemsList[position]
        holder.id = item.id
        holder.cardView.setCardBackgroundColor(item.color)
        holder.txtTitle.text = item.title
        holder.cardView.tag = item.id

        if (item.type == ArticleType.INCOME) {
            holder.cardView.setOnLongClickListener { view ->
                val clipData = ClipData.newPlainText("", "")
                val dsb = View.DragShadowBuilder(view)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.startDragAndDrop(clipData, dsb, view, 0)
                } else {
                    view.startDrag(clipData, dsb, view, 0)
                }
                view.visibility = View.INVISIBLE
                true
            }
        } else {
            holder.cardView.setOnDragListener(holder)
        }
    }


    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun update(list: List<OperationArticleItem>) {
        itemsList = list
        notifyDataSetChanged()
    }
}


class AccountAdapter(var itemsList: List<OperationAccountItem>, val context: Context, val presenter: OperationPresenter)
    : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {


    inner class AccountViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnDragListener {

        var id: Long = -1L
        var cardView: CardView = view.findViewById(R.id.thumbnail_account)
        var txtTitle: TextView = view.findViewById(R.id.account_title)
        var txtSum: TextView = view.findViewById(R.id.account_sum)
        var isDragging: Boolean = false


        override fun onDrag(view: View, dragEvent: DragEvent): Boolean {
            val dragAction = dragEvent.action
            val dragView = dragEvent.localState as View

            when (dragAction) {
                DragEvent.ACTION_DRAG_EXITED -> isDragging = false
                DragEvent.ACTION_DRAG_ENTERED -> isDragging = true
                DragEvent.ACTION_DRAG_ENDED -> if (!dragEvent.result) {
                    dragView.visibility = View.VISIBLE
                }
                DragEvent.ACTION_DROP -> if (isDragging) {


                    val parentView = dragView.parent
                    if (parentView is RecyclerView) {
                        val accountId = view.tag as Long
                        val sourceId = dragView.tag as Long
                        if (parentView.tag as String == ACCOUNTS_RECYCLER_VIEW_TAG) {
                            presenter.requireTransitionTransaction(sourceId, accountId)
                        } else {
                            presenter.requireIncomeTransaction(sourceId, accountId)
                        }
                    }
                    dragView.visibility = View.VISIBLE
                }
            }
            return true
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.thumbnail_account, parent, false)

        return AccountViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {

        holder.id = itemsList[position].id
        holder.cardView.setCardBackgroundColor(itemsList[position].color)
        holder.txtTitle.text = itemsList[position].title
        holder.txtSum.text = itemsList[position].sum
        holder.cardView.tag = itemsList[position].id
        holder.cardView.setOnDragListener(holder)


        holder.cardView.setOnLongClickListener { view ->
            val clipData = ClipData.newPlainText("", "")
            val dsb = View.DragShadowBuilder(view)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.startDragAndDrop(clipData, dsb, view, 0)
            } else {
                view.startDrag(clipData, dsb, view, 0)
            }
            view.visibility = View.INVISIBLE
            true
        }

    }


    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun update(list: List<OperationAccountItem>) {
        itemsList = list
        notifyDataSetChanged()
    }

}
