package com.zavijavasoft.yafina.ui.operation

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
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
                    Triple(restMap, articlesCommon, accountsList)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { res ->
                    viewState.update(res.first, res.second, res.third)
                }


    }

    override fun acceptOperation(request: TransactionRequest) {

        val articles = tracker.getArticlesList()
        val accounts = tracker.getAccountsList()
        Single.zip(articles, accounts,
                BiFunction { articlesList: List<ArticleEntity>,
                             accountsList: List<AccountEntity> ->
                    val accountId = when (request.type) {
                        TransactionType.OUTCOME -> request.accountFrom
                        TransactionType.INCOME -> request.accountTo
                        TransactionType.TRANSITION -> request.accountFrom
                        }
                    val article = when (request.type) {
                        TransactionType.OUTCOME -> request.articleTo
                        TransactionType.INCOME -> request.articleFrom
                        TransactionType.TRANSITION -> ARTICLE_OUTCOME_TRANSITION_SPECIAL_ID
                    }
                    val transaction = if (request.isScheduled) {
                        ScheduledTransactionInfo(sum = request.maxSum, article = article,
                                accountId = accountId, datetime = Date(), comment = "",
                                period = TransactionScheduleTimeUnit.values()[request.period])
                    } else {
                        OneTimeTransactionInfo(sum = request.maxSum, article = article,
                                accountId = accountId, datetime = Date(), comment = "")
                    }
                    tracker.addTransaction(transaction)
                    if (request.type == TransactionType.TRANSITION) {
                        tracker.addTransaction(OneTimeTransactionInfo(sum = request.maxSum,
                                article = ARTICLE_INCOME_TRANSITION_SPECIAL_ID,
                                accountId = request.accountFrom, datetime = Date(), comment = ""))
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    needUpdate()
                }
    }

    override fun cancelOperation() {

    }

    override fun requireIncomeTransaction(articleId: Long, accountId: Long) {

        tracker.getAccountsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
            val account = it.find { it.id == accountId }
            if (account != null) {

                viewState.requireTransaction(TransactionRequest(TransactionType.INCOME, false,
                        Float.NaN, account.currency,
                        -1, accountId,
                        articleId, -1, -1, -1))
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
                            launch (UI) {
                                viewState.notifyInsufficientMoney(account.name, formattedRest, "")
                            }
                        } else {
                            launch (UI) {
                                viewState.requireTransaction(TransactionRequest(TransactionType.OUTCOME,
                                        false, rest, account.currency,
                                        accountId, -1,
                                        -1, articleId, -1, -1))
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
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
                            launch (UI) {
                                viewState.notifyInsufficientMoney(account.name, formattedRest, "")
                            }

                        } else {
                            launch (UI) {
                                viewState.requireTransaction(TransactionRequest(TransactionType.OUTCOME,
                                        false, rest, account.currency,
                                        accountFromId, accountToId,
                                        -1, -1, -1, -1))
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }
}