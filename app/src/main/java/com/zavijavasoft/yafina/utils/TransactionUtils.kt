package com.zavijavasoft.yafina.utils

import com.zavijavasoft.yafina.model.TransactionInfo

enum class TransactionType {
    INCOME,
    OUTCOME,
    TRANSITION
}

fun getOwnerAccount(transaction: TransactionInfo) = when (transaction.transactionType) {
        TransactionType.INCOME -> transaction.accountIdTo
        else -> transaction.accountIdFrom
    }