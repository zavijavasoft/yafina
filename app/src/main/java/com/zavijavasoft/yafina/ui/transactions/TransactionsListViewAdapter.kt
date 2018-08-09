package com.zavijavasoft.yafina.ui.transactions

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.model.AccountEntity
import com.zavijavasoft.yafina.model.ArticleEntity
import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.utils.ColorSelector

class TransactionsListViewAdapter(
        var itemsList: List<Triple<TransactionInfo, ArticleEntity, AccountEntity>>,
        val context: Context,
        val presenter: TransactionsListPresenter,
        private val listener: OnClickListener
) : RecyclerView.Adapter<TransactionsListViewAdapter.TransactionViewHolder>() {


    inner class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var id: Long = -1L
        var cardView: CardView = view.findViewById(R.id.thumbnail_transaction)
        var image: ImageView = view.findViewById(R.id.transaction_image)
        var txtActicle: TextView = view.findViewById(R.id.transaction_article_title)
        var txtAccount: TextView = view.findViewById(R.id.transaction_account_title)
        var txtSum: TextView = view.findViewById(R.id.transaction_sum)
        val btnEdit: ImageButton = view.findViewById(R.id.btnEdit)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.thumbnail_transaction, parent, false)

        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val (transaction, article, account) = itemsList[position]

        holder.id = transaction.transactionId
        holder.cardView.setCardBackgroundColor(ColorSelector.randomColor)
        holder.txtAccount.text = account.name
        holder.txtActicle.text = article.title
        holder.txtSum.text = transaction.sum.toString()
        holder.btnEdit.setOnClickListener {
            listener.onEdit(transaction)
        }
    }


    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun update(list: List<Triple<TransactionInfo, ArticleEntity, AccountEntity>>) {
        itemsList = list
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onEdit(transaction: TransactionInfo)
    }
}

