package com.zavijavasoft.yafina.ui.settings.article.edit

import com.arellomobile.mvp.MvpView
import com.zavijavasoft.yafina.model.ArticleEntity
import com.zavijavasoft.yafina.model.ArticleTemplateEntity

interface ArticleEditView: MvpView {
    fun update(article: ArticleEntity, articleTemplate: ArticleTemplateEntity)
    fun close()
}