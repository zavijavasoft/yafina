package com.zavijavasoft.yafina.ui.transactions

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.FinanceTracker
import com.zavijavasoft.yafina.model.TransactionInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class TransactionsListPresenterImpl @Inject constructor(val tracker: FinanceTracker)
    : MvpPresenter<TransactionsListView>(), TransactionsListPresenter {


    override fun needUpdate() {
        tracker.retrieveTransactions().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe { it ->
                    viewState.update(it)
                }
    }

    override fun addTransaction(transactionInfo: TransactionInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateTransaction(transactionInfo: TransactionInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun selectTransaction(transactionId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unselectTransaction(transactionId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unselectAllTransactions() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeSelected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}