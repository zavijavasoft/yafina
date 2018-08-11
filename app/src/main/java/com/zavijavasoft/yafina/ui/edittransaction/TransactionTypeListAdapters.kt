package com.zavijavasoft.yafina.ui.edittransaction

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.zavijavasoft.yafina.R
import kotlinx.android.synthetic.main.item_with_selection.view.*

class SelectionListRecyclerListAdapter(
        private val data: MutableList<SelectionItem>
): RecyclerView.Adapter<SelectionListRecyclerListAdapter.ViewHolder>() {

    private var selectedPos: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_with_selection, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.title.text = item.title
        holder.isSelected.visibility = if (position == selectedPos) View.VISIBLE else View.INVISIBLE
    }

    fun update(newData: List<SelectionItem>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
        selectedPos = -1
    }

    fun getData() = data

    fun getLastSelectedItem(): SelectionItem? = if (selectedPos != -1) data[selectedPos] else null

    fun setSelected(position: Int) {
        selectedPos = position
        notifyItemChanged(selectedPos)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView = itemView.tvTitle
        val isSelected: ImageView = itemView.ivIsSelected

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (selectedPos != -1) {
                notifyItemChanged(selectedPos)
            }
            selectedPos = layoutPosition
            notifyItemChanged(selectedPos)
        }
    }
}

data class SelectionItem(
        val id: Long,
        val title: String
)