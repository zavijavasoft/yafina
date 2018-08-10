package com.zavijavasoft.yafina.ui.operation

//import com.zavijavasoft.yafina.utils.createTransactionFromRequest
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.*
import com.zavijavasoft.yafina.utils.TransactionType
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@InjectViewState
class OperationPresenterImpl @Inject constructor(
        private val tracker: FinanceTracker,
        private val articleTemplateStorage: ArticleTemplateStorage
)
    : MvpPresenter<OperationView>(), OperationPresenter {

    override fun needUpdate() {

        val articles = tracker.getArticlesList()
        val accounts = tracker.getAccountsList()
        val rests = tracker.getRests()

        Single.zip(articles, accounts, rests,
                Function3 { articlesList: List<ArticleEntity>,
                            accountsList: List<AccountEntity>,
                            restMap: Map<Long, Float> ->
                    Triple(restMap, articlesList, accountsList)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { res ->
                    viewState.update(res.first, res.second, res.third)
                }


    }

    override fun cancelOperation() {

    }

    override fun requireIncomeTransaction(articleId: Long, accountId: Long) {
        Single.fromCallable {
            getTemplatedTransaction(OneTimeTransactionInfo(TransactionType.INCOME, 0f,
                    Date(), accountId, articleId = articleId))
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { tr ->
                    viewState.requireTransaction(tr)
                }

    }

    override fun requireOutcomeTransaction(articleId: Long, accountId: Long) {
        Single.fromCallable {
            getTemplatedTransaction(OneTimeTransactionInfo(
                    TransactionType.OUTCOME, 0f, Date(), 0,
                    articleId = articleId, accountIdFrom = accountId))
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { tr ->
                    viewState.requireTransaction(tr)
                }
    }

    override fun requireTransitionTransaction(accountFromId: Long, accountToId: Long) {
        viewState.requireTransaction(OneTimeTransactionInfo(
                TransactionType.TRANSITION, 0f, Date(), accountToId,
                accountIdFrom = accountFromId))
    }

    private fun getTemplatedTransaction(transaction: OneTimeTransactionInfo): TransactionInfo {
        val template = articleTemplateStorage.getTemplateByArticleId(transaction.articleId)
        return if (!template.isScheduled) transaction.copy(comment = template.defaultComment)
        else ScheduledTransactionInfo(transaction.transactionType,
        transaction.sum, transaction.datetime, template.period, transaction.accountIdTo,
                template.defaultComment, transaction.accountIdFrom, transaction.articleId)
    }
}