package com.zavijavasoft.yafina.ui.edittransaction

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class EditTransactionPresenterImpl @Inject constructor(
        private val transactionStorage: TransactionStorage,
        private val articlesStorage: ArticlesStorage,
        private val accountsStorage: AccountsStorage,
        private val currencyStorage: CurrencyStorage
) : MvpPresenter<EditTransactionView>(), EditTransactionPresenter {

    override fun getAccounts() {
        accountsStorage.getAccounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { accounts ->
                    viewState.setAccounts(accounts)
                }
    }

    override fun getCurrencies() {
        currencyStorage.getCurrencyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { currencies ->
                    viewState.setCurrencies(currencies)
                }
    }

    override fun saveTransaction(transactionInfo: TransactionInfo, isNew: Boolean) {
        val res = if (isNew) transactionStorage.add(transactionInfo) else
            transactionStorage.update(transactionInfo)
        res.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewState.close()
                }
    }

    override fun deleteTransaction(transactionInfo: TransactionInfo) {
        transactionStorage.remove(transactionInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewState.close()
                }
    }

    override fun getIncomingsList() {
        articlesStorage.getArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { articles ->
                    viewState.setIncomings(articles.filter { it.type == ArticleType.INCOME })
                }
    }

    override fun getOutgoingsList() {
        articlesStorage.getArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { articles ->
                    viewState.setIncomings(articles.filter { it.type == ArticleType.OUTCOME })
                }
    }

    override fun getAccountsExcept(accountId: Long) {
        accountsStorage.getAccounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { accounts ->
                    viewState.setAccounts(accounts.filterNot { it.id == accountId }, true)
                }
    }

    override fun getAccount(accountId: Long) {
        accountsStorage.getAccountById(accountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { account ->
                    viewState.setAccount(account)
                }
    }
}