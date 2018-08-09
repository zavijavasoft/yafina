package com.zavijavasoft.yafina.ui.operation

interface OperationPresenter {
    fun cancelOperation()
    fun needUpdate()

    fun requireIncomeTransaction(articleId: Long, accountId: Long)
    fun requireOutcomeTransaction(articleId: Long, accountId: Long)
    fun requireTransitionTransaction(accountFromId: Long, accountToId: Long)
}