package com.zavijavasoft.yafina.model

import android.os.Parcelable
import com.zavijavasoft.yafina.utils.TransactionType
import kotlinx.android.parcel.Parcelize
import java.util.*

sealed class TransactionInfo(
        open val transactionId: Long,
        open val transactionType: TransactionType,
        open val sum: Float,
        open val datetime: Date,
        open val accountIdTo: Long,
        open val comment: String,
        open val accountIdFrom: Long,
        open val articleId: Long
        ): Parcelable {

    constructor(transactionType: TransactionType, sum: Float, datetime: Date, accountIdTo: Long,
                comment: String, accountIdFrom: Long, articleId: Long)
            : this(datetime.time, transactionType, sum, datetime, accountIdTo, comment,
            accountIdFrom, articleId)
}

@Parcelize
data class OneTimeTransactionInfo(
        override val transactionType: TransactionType,
        override val sum: Float,
        override val datetime: Date,
        override val accountIdTo: Long,
        override val comment: String = "",
        override val accountIdFrom: Long = 0,
        override val articleId: Long = 0
): TransactionInfo(transactionType, sum, datetime, accountIdTo, comment, accountIdFrom, articleId)

@Parcelize
data class ScheduledTransactionInfo(
        override val transactionType: TransactionType,
        override val sum: Float,
        override val datetime: Date,
        val period: TransactionScheduleTimeUnit,
        override val accountIdTo: Long,
        override val comment: String = "",
        override val accountIdFrom: Long = 0,
        override val articleId: Long = 0
): TransactionInfo(transactionType, sum, datetime, accountIdTo, comment, accountIdFrom, articleId), Parcelable

enum class TransactionScheduleTimeUnit {
    WEEK,
    MONTH,
    YEAR
}