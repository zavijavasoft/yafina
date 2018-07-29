package com.zavijavasoft.yafina.core

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.FinanceTracker
import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.ui.operation.OperationView
import rx.Single
import javax.inject.Inject

@InjectViewState
class OperationPresenterImpl @Inject constructor(private val tracker: FinanceTracker)
    : MvpPresenter<OperationView>(), OperationPresenter {

    override fun needUpdate() {

        val articles = tracker.getArticlesList()
        val accounts = tracker.getAccountsList()
        val rests = tracker.getRests()

        Single.zip(articles, accounts, rests) { articlesList, accountsList, restMap ->
            viewState.update(restMap, articlesList, accountsList)
        }.subscribe()


    }

    override fun acceptOperation(transaction: TransactionInfo) {

    }

    override fun cancelOperation() {

    }

    override fun requireIncomeTransaction(articleId: Long, accountId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requireOutcomeTransaction(articleId: Long, accountId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requireTransitionTransaction(accountFromId: Long, accountToId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}