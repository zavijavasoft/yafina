package com.zavijavasoft.yafina.core

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.AccountEntity
import com.zavijavasoft.yafina.model.AccountsStorage
import com.zavijavasoft.yafina.model.CurrencyStorage
import com.zavijavasoft.yafina.ui.settings.account.edit.AccountEditView
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class AccountEditPresenterImpl @Inject constructor(
        private val accountsStorage: AccountsStorage,
        private val currencyStorage: CurrencyStorage
        ) : MvpPresenter<AccountEditView>(), AccountEditPresenter {

    override fun update(accountId: Long) {
        Single.zip(
                accountsStorage.getAccountById(accountId),
                currencyStorage.getCurrencyList(),
                BiFunction {
                    accounts: AccountEntity,
                    currencies: List<String> ->
                    Pair(accounts, currencies)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { (account, currencies) ->
                    viewState.updateCurrencies(currencies)
                    viewState.update(account)
                }
    }

    override fun update() {
        currencyStorage.getCurrencyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { currencies ->
                    viewState.updateCurrencies(currencies)
                }
    }

    override fun save(account: AccountEntity) {
        val result: Completable = if (account.id == 0L) {
            accountsStorage.addAccount(account)
        } else {
            accountsStorage.updateAccount(account)
        }
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewState.close()
                }
    }
}