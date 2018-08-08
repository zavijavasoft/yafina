package com.zavijavasoft.yafina.core

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.FinanceTracker
import com.zavijavasoft.yafina.ui.settings.account.list.AccountListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class AccountListPresenterImpl @Inject constructor(private val tracker: FinanceTracker)
    : MvpPresenter<AccountListView>(), AccountListPresenter {

    override fun newAccount() {
        viewState.requireAccountCreate()
    }

    override fun edit(accountId: Long) {
        viewState.requireAccountEdit(accountId)
    }

    override fun needUpdate() {
        tracker.getAccountsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    viewState.update(list)
                }
    }
}