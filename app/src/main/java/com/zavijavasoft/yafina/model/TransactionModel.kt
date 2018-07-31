package com.zavijavasoft.yafina.model

import java.util.*


data class TransactionInfo(
        val sum: Float,
        val article: Long,
        val datetime: Date,
        val accountId: Long,
        val comment: String = ""
) {
    val transactionId = datetime.time
}