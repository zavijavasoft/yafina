package com.zavijavasoft.yafina.ui.operation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.zavijavasoft.yafina.model.AccountEntity
import com.zavijavasoft.yafina.model.ArticleEntity
import com.zavijavasoft.yafina.model.TransactionInfo

interface OperationView : MvpView {
    fun update(rests: Map<Long, Float>, arcticles: List<ArticleEntity>, accounts: List<AccountEntity>)
    @StateStrategyType(SkipStrategy::class)
    fun requireTransaction(transaction: TransactionInfo)
}