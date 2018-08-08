package com.zavijavasoft.yafina.ui.settings.article.list

import com.arellomobile.mvp.MvpView
import com.zavijavasoft.yafina.model.ArticleEntity

interface ArticleListView : MvpView {
    fun update(articles: List<ArticleEntity>)
    fun requireArticleEdit(articleId: Long)
    fun requireArticleCreate()
}