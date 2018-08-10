package com.zavijavasoft.yafina.ui.settings.article.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.model.ArticleEntity
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleListRecyclerViewAdapter(
        private val data: MutableList<ArticleEntity>,
        private val listener: (Long) -> Unit
) : RecyclerView.Adapter<ArticleListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = data[position]

        holder.articleTitle.text = article.title
        holder.btnEdit.setOnClickListener {
            listener.invoke(article.articleId)
        }
    }

    fun update(newData: List<ArticleEntity>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val articleTitle: TextView = itemView.tvArticleTitle
        val btnEdit: ImageButton = itemView.btnEdit
    }
}