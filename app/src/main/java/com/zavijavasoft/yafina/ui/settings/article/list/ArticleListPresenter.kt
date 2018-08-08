package com.zavijavasoft.yafina.ui.settings.article.list

interface ArticleListPresenter {
    fun needUpdate()
    fun newArticle()
    fun edit(articleId: Long)
}