package com.zavijavasoft.yafina.ui.settings.account.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.model.AccountEntity
import kotlinx.android.synthetic.main.item_account.view.*

class AccountListRecyclerViewAdapter(
        private val data: MutableList<AccountEntity>,
        private val listener: (Long) -> Unit
) : RecyclerView.Adapter<AccountListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = data[position]

        holder.accountName.text = account.name
        holder.btnEdit.setOnClickListener {
            listener.invoke(account.id)
        }
    }

    fun update(newData: List<AccountEntity>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val accountName: TextView = itemView.tvAccountName
        val btnEdit: ImageButton = itemView.btnEdit
    }
}