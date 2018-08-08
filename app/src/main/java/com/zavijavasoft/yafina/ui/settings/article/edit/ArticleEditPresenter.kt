package com.zavijavasoft.yafina.ui.settings.article.edit

import com.zavijavasoft.yafina.model.ArticleEntity

interface ArticleEditPresenter {
    fun update(articleId: Long)
    fun save(article: ArticleEntity)
}