package com.zavijavasoft.yafina.model

import java.util.*


sealed class TransactionInfo {
    abstract val transactionId: Long
    abstract val sum: Float
    abstract val article: Long
    abstract val datetime: Date
    abstract val accountId: Long
    abstract val comment: String
}

data class OneTimeTransactionInfo(
        override val sum: Float,
        override val article: Long,
        override val datetime: Date,
        override val accountId: Long,
        override val comment: String = ""
) :TransactionInfo() {
    override val transactionId = datetime.time
}

data class ScheduledTransactionInfo(
        override val sum: Float,
        override val article: Long,
        override val datetime: Date,
        override val accountId: Long,
        override val comment: String = "",
        val period: TransactionScheduleTimeUnit
): TransactionInfo() {
    override val transactionId = datetime.time
}

enum class TransactionScheduleTimeUnit {
    WEEK,
    MONTH,
    YEAR
}