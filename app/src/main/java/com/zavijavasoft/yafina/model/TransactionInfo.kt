package com.zavijavasoft.yafina.model

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