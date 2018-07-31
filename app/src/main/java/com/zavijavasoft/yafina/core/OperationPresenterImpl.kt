package com.zavijavasoft.yafina.core

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.*
import com.zavijavasoft.yafina.ui.operation.OperationView
import com.zavijavasoft.yafina.ui.operation.TransactionRequest
import com.zavijavasoft.yafina.ui.operation.TransactionType
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import java.util.*
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
                    val articlesCommon = articlesList.asSequence()
                            .filter { it.articleId != ARTICLE_INCOME_TRANSITION_SPECIAL_ID }
                            .filter { it.articleId != ARTICLE_OUTCOME_TRANSITION_SPECIAL_ID }
                            .toList()
                    viewState.update(restMap, articlesCommon, accountsList)
                }).subscribe()


    }

    override fun acceptOperation(request: TransactionRequest) {

        val articles = tracker.getArticlesList()
        val accounts = tracker.getAccountsList()
        Single.zip(articles, accounts,
                BiFunction { articlesList: List<ArticleEntity>,
                             accountsList: List<AccountEntity> ->
                    when (request.type) {
                        TransactionType.OUTCOME ->
                            tracker.addTransaction(TransactionInfo(sum = request.maxSum,
                                    article = request.articleTo,
                                    accountId = request.accountFrom, datetime = Date(), comment = ""))
                        TransactionType.INCOME ->
                            tracker.addTransaction(TransactionInfo(sum = request.maxSum,
                                    article = request.articleFrom,
                                    accountId = request.accountTo, datetime = Date(), comment = ""))
                        TransactionType.TRANSITION -> {
                            tracker.addTransaction(TransactionInfo(sum = request.maxSum,
                                    article = ARTICLE_OUTCOME_TRANSITION_SPECIAL_ID,
                                    accountId = request.accountFrom, datetime = Date(), comment = ""))
                            tracker.addTransaction(TransactionInfo(sum = request.maxSum,
                                    article = ARTICLE_INCOME_TRANSITION_SPECIAL_ID,
                                    accountId = request.accountFrom, datetime = Date(), comment = ""))
                        }
                    }

                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun cancelOperation() {

    }

    override fun requireIncomeTransaction(articleId: Long, accountId: Long) {

        tracker.getAccountsList().subscribe { it ->
            val account = it.find { it.id == accountId }
            if (account != null) {

                viewState.requireTransaction(TransactionRequest(TransactionType.INCOME,
                        Float.NaN, account.currency,
                        -1, accountId,
                        articleId, -1))
            }
        }

    }

    override fun requireOutcomeTransaction(articleId: Long, accountId: Long) {
        val accounts = tracker.getAccountsList()
        val rests = tracker.getRests()

        Single.zip(accounts, rests,
                BiFunction { accountsList: List<AccountEntity>,
                             restMap: Map<Long, Float> ->
                    val account = accountsList.find { it.id == accountId }
                    if (account != null) {

                        val rest = restMap[accountId] ?: 0.0f
                        if (rest <= 0.0f) {
                            val formattedRest = "$rest ${account.currency}"
                            viewState.notifyInsufficientMoney(account.name, formattedRest, "")
                        } else {

                            viewState.requireTransaction(TransactionRequest(TransactionType.OUTCOME,
                                    rest, account.currency,
                                    accountId, -1,
                                    -1, articleId))
                        }
                    }
                }).subscribe()
    }

    override fun requireTransitionTransaction(accountFromId: Long, accountToId: Long) {
        val accounts = tracker.getAccountsList()
        val rests = tracker.getRests()

        Single.zip(accounts, rests,
                BiFunction { accountsList: List<AccountEntity>,
                             restMap: Map<Long, Float> ->
                    val account = accountsList.find { it.id == accountFromId }
                    if (account != null) {

                        val rest = restMap[accountFromId] ?: 0.0f
                        if (rest <= 0.0f) {
                            val formattedRest = "$rest ${account.currency}"
                            viewState.notifyInsufficientMoney(account.name, formattedRest, "")
                        } else {

                            viewState.requireTransaction(TransactionRequest(TransactionType.OUTCOME,
                                    rest, account.currency,
                                    accountFromId, accountToId,
                                    -1, -1))
                        }
                    }
                }).subscribe()
    }
}