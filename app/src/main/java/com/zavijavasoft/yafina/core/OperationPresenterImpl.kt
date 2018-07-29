package com.zavijavasoft.yafina.core

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.AccountEntity
import com.zavijavasoft.yafina.model.ArticleEntity
import com.zavijavasoft.yafina.model.FinanceTracker
import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.ui.operation.OperationView
import io.reactivex.Single
import io.reactivex.functions.Function3
import javax.inject.Inject

@InjectViewState
class OperationPresenterImpl @Inject constructor(private val tracker: FinanceTracker)
    : MvpPresenter<OperationView>(), OperationPresenter {

    override fun needUpdate() {

        val articles = tracker.getArticlesList()
        val accounts = tracker.getAccountsList()
        val rests = tracker.getRests()

        Single.zip(articles, accounts, rests,
                Function3 { articlesList: List<ArticleEntity>,
                            accountsList: List<AccountEntity>,
                            restMap: Map<Long, Float> ->
            viewState.update(restMap, articlesList, accountsList)
                }).subscribe()


    }

    override fun acceptOperation(transaction: TransactionInfo) {

    }

    override fun cancelOperation() {

    }

    override fun requireIncomeTransaction(articleId: Long, accountId: Long) {
    }

    override fun requireOutcomeTransaction(articleId: Long, accountId: Long) {
    }

    override fun requireTransitionTransaction(accountFromId: Long, accountToId: Long) {
    }
}