package com.zavijavasoft.yafina.ui.transactions

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.core.TransactionsListPresenter
import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.utils.ColorSelector

class TransactionsListViewAdapter(var itemsList: List<TransactionInfo>, val context: Context, val presenter: TransactionsListPresenter)
    : RecyclerView.Adapter<TransactionsListViewAdapter.TransactionViewHolder>() {


    inner class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var id: Long = -1L
        var cardView: CardView = view.findViewById(R.id.thumbnail_transaction)
        var image: ImageView = view.findViewById(R.id.transaction_image)
        var txtActicle: TextView = view.findViewById(R.id.transaction_article_title)
        var txtAccount: TextView = view.findViewById(R.id.transaction_account_title)
        var txtSum: TextView = view.findViewById(R.id.transaction_sum)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.thumbnail_transaction, parent, false)

        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {

        val item = itemsList[position]
        holder.id = item.transactionId
        holder.cardView.setCardBackgroundColor(ColorSelector.randomColor)
        holder.txtAccount.text = "Счет такой-то"
        holder.txtActicle.text = "Статья такая-то"
        holder.txtSum.text = item.sum.toString()

    }


    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun update(list: List<TransactionInfo>) {
        itemsList = list
        notifyDataSetChanged()
    }
}

