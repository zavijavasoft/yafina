package com.zavijavasoft.yafina.core

import com.zavijavasoft.yafina.model.TransactionInfo

interface OperationPresenter {
    fun acceptOperation(transaction: TransactionInfo)
    fun cancelOperation()
    fun needUpdate()

    fun requireIncomeTransaction(articleId: Long, accountId: Long)
    fun requireOutcomeTransaction(articleId: Long, accountId: Long)
    fun requireTransitionTransaction(accountFromId: Long, accountToId: Long)
}