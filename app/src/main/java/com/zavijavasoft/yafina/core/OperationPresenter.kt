package com.zavijavasoft.yafina.core

import com.zavijavasoft.yafina.ui.operation.TransactionRequest

interface OperationPresenter {
    fun acceptOperation(request: TransactionRequest)
    fun cancelOperation()
    fun needUpdate()

    fun requireIncomeTransaction(articleId: Long, accountId: Long)
    fun requireOutcomeTransaction(articleId: Long, accountId: Long)
    fun requireTransitionTransaction(accountFromId: Long, accountToId: Long)
}