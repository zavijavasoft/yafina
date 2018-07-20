package com.zavijavasoft.yafina.core

import java.util.*

enum class TransactionType {
    INCOME,
    OUTCOME
}

data class TransactionInfo(
        val type: TransactionType,
        val currency: String,
        val sum: Float,
        val article: String,
        val datetime: Date
) {
    val transactionId = datetime.time
}