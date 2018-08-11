package com.zavijavasoft.yafina.ui.transactions

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.*
import com.zavijavasoft.yafina.utils.getOwnerAccount
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class TransactionsListPresenterImpl @Inject constructor(
        private val tracker: FinanceTracker,
        private val accountsStorage: AccountsStorage,
        private val articlesStorage: ArticlesStorage
) : MvpPresenter<TransactionsListView>(), TransactionsListPresenter {

    override fun needUpdate() {
        Single.fromCallable {
            val transactions = tracker.retrieveTransactions().blockingGet()
            val res: MutableList<Triple<TransactionInfo, ArticleEntity, AccountEntity>> = mutableListOf()
            for (tr in transactions) {
                val ownerId = getOwnerAccount(tr)
                val receiverId = if (tr.accountIdFrom == ownerId) tr.accountIdTo else tr.accountIdFrom
                val account = accountsStorage.getAccountById(getOwnerAccount(tr)).blockingGet()
                val article = if (tr.articleId != 0L) {
                    articlesStorage.getArticleById(tr.articleId).blockingGet()
                } else {
                    val acc = accountsStorage.getAccountById(receiverId).blockingGet()
                    ArticleEntity(0, ArticleType.INCOME, acc.name)
                }

                res.add(Triple(tr, article, account))
            }
            res
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { res ->
                    viewState.update(res)
                }
    }
}