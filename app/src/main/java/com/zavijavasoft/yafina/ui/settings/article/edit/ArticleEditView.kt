package com.zavijavasoft.yafina.ui.settings.article.edit

import com.arellomobile.mvp.MvpView
import com.zavijavasoft.yafina.model.ArticleEntity

interface ArticleEditView: MvpView {
    fun update(article: ArticleEntity)
    fun close()
}