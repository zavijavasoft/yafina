package com.zavijavasoft.yafina.core

import com.zavijavasoft.yafina.model.TransactionInfo

interface TransactionsListPresenter {
    fun needUpdate()
    fun addTransaction(transactionInfo: TransactionInfo)
    fun updateTransaction(transactionInfo: TransactionInfo)
    fun selectTransaction(transactionId: Long)
    fun unselectTransaction(transactionId: Long)
    fun unselectAllTransactions()
    fun removeSelected()
}