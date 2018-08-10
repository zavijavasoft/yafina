package com.zavijavasoft.yafina.ui.balance

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.github.mikephil.charting.charts.Chart
import com.zavijavasoft.yafina.R
import kotlinx.android.synthetic.main.item_chart.view.*

class ChartRecyclerViewAdapter(
        private val data: MutableList<Chart<*>>
) : RecyclerView.Adapter<ChartRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_chart, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.container.addView(item)
    }

    fun add(chart: Chart<*>) {
        data.add(chart)
        notifyItemInserted(data.size-1)
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val container: FrameLayout = itemView.container
    }
}