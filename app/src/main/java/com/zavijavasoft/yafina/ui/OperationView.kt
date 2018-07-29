package com.zavijavasoft.yafina.ui

import com.arellomobile.mvp.MvpView
import com.zavijavasoft.yafina.model.AccountEntity
import com.zavijavasoft.yafina.model.ArticleEntity

interface OperationView : MvpView {
    fun update(rests: Map<Long, Float>, arcticles: List<ArticleEntity>, accounts: List<AccountEntity>)
}