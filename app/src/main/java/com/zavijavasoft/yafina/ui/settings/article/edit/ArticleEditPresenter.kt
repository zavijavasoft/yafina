package com.zavijavasoft.yafina.ui.settings.article.edit

import com.zavijavasoft.yafina.model.ArticleEntity
import com.zavijavasoft.yafina.model.ArticleTemplateEntity

interface ArticleEditPresenter {
    fun update(articleId: Long)
    fun save(article: ArticleEntity, template: ArticleTemplateEntity)
    fun delete(article: ArticleEntity, template: ArticleTemplateEntity)
}