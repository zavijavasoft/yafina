package com.zavijavasoft.yafina.ui.balance

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.model.BalanceChunk
import com.zavijavasoft.yafina.utils.ColorSelector


class BalanceAdapter(var itemsList: List<BalanceChunk>, val context: Context, val presenter: BalancePresenter)
    : RecyclerView.Adapter<BalanceAdapter.BalanceViewHolder>() {


    inner class BalanceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var id: Long = -1L
        var cardView: CardView = view.findViewById(R.id.thumbnail_balance)
        var txtCurrency: TextView = view.findViewById(R.id.balance_currency)
        var txtSum: TextView = view.findViewById(R.id.balance_sum)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.thumbnail_balance, parent, false)

        return BalanceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {

        val item = itemsList[position]
        holder.cardView.setCardBackgroundColor(ColorSelector.getColorByLeadingLetter(item.currency[0]))
        holder.txtCurrency.text = item.currency
        holder.txtSum.text = item.sum.toString()

    }


    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun update(list: List<BalanceChunk>) {
        itemsList = list
        notifyDataSetChanged()
    }
}
