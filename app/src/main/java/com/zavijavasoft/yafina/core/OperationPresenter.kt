package com.zavijavasoft.yafina.core

import com.zavijavasoft.yafina.model.TransactionInfo

interface OperationPresenter {
    fun acceptOperation(transaction: TransactionInfo)
    fun cancelOperation()
    fun needUpdate()
}