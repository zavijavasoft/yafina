package com.zavijavasoft.yafina.ui.edittransaction

import com.arellomobile.mvp.MvpView
import com.zavijavasoft.yafina.model.AccountEntity
import com.zavijavasoft.yafina.model.ArticleEntity

interface EditTransactionView: MvpView {
    fun setAccount(account: AccountEntity)
    fun setAccounts(accounts: List<AccountEntity>)
    fun setAccounts(accounts: List<AccountEntity>, exceptOne: Boolean)
    fun setIncomings(incomings: List<ArticleEntity>)
    fun setOutgoings(outgoings: List<ArticleEntity>)
    fun setCurrencies(currencies: List<String>)
    fun close()
}