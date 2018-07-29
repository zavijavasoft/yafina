package com.zavijavasoft.yafina.ui

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zavijavasoft.yafina.R


data class OperationArticleItem(val id: Long, val title: String, val color: Int)
data class OperationAccountItem(val id: Long, val title: String, val sum: String, val color: Int)


class ArticleAdapter(var itemsList: List<OperationArticleItem>, val context: Context)
    : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {


    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var id: Long = -1L
        var cardView: CardView = view.findViewById(R.id.thumbnail_article)
        var txtTitle: TextView = view.findViewById(R.id.article_title)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.thumbnail_article, parent, false)

        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        holder.id = itemsList[position].id
        holder.cardView.setCardBackgroundColor(itemsList[position].color)
        holder.txtTitle.text = itemsList[position].title

    }


    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun update(list: List<OperationArticleItem>) {
        itemsList = list
        notifyDataSetChanged()
    }
}


class AccountAdapter(var itemsList: List<OperationAccountItem>, val context: Context)
    : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {


    inner class AccountViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var id: Long = -1L
        var cardView: CardView = view.findViewById(R.id.thumbnail_account)
        var txtTitle: TextView = view.findViewById(R.id.account_title)
        var txtSum: TextView = view.findViewById(R.id.account_sum)

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

/*        holder.imageView.setOnClickListener(object : View.OnClickListener() {
            fun onClick(v: View) {
                val list = itemsList.get(position).txt.toString()
                Toast.makeText(this@MainActivity, list, Toast.LENGTH_SHORT).show()
            }

        })
*/
    }


    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun update(list: List<OperationAccountItem>) {
        itemsList = list
        notifyDataSetChanged()
    }

}
