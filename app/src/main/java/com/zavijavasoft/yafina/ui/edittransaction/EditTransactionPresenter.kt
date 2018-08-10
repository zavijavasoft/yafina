package com.zavijavasoft.yafina.ui.edittransaction

import com.zavijavasoft.yafina.model.TransactionInfo

interface EditTransactionPresenter {
    fun saveTransaction(transactionInfo: TransactionInfo, isNew: Boolean)
    fun deleteTransaction(transactionInfo: TransactionInfo)
    fun getIncomingsList()
    fun getOutgoingsList()
    fun getAccountsExcept(accountId: Long)
    fun getAccount(accountId: Long)
    fun getCurrencies()
    fun getAccounts()
}