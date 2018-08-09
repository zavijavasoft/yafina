package com.zavijavasoft.yafina.utils

import android.os.Parcelable
import com.zavijavasoft.yafina.model.TransactionInfo
import kotlinx.android.parcel.Parcelize

const val TRANSACTION_REQUEST_TAG = "TRANSACTION_REQUEST_TAG"

enum class TransactionType {
    INCOME,
    OUTCOME,
    TRANSITION
}

@Parcelize
data class TransactionRequest(
        val transactionId: Long, val type: TransactionType, val isScheduled: Boolean,
        val sum: Float, val currency: String, val accountFrom: Long,
        val accountTo: Long, val articleFrom: Long, val articleTo: Long,
        val day: Int, val period: Int)
    : Parcelable

fun getOwnerAccount(transaction: TransactionInfo) = when (transaction.transactionType) {
        TransactionType.INCOME -> transaction.accountIdTo
        else -> transaction.accountIdFrom
    }

//fun createTransactionFromRequest(request: TransactionRequest): TransactionInfo {
//    val accountId = when (request.type) {
//        TransactionType.OUTCOME -> request.accountFrom
//        TransactionType.INCOME -> request.accountTo
//        TransactionType.TRANSITION -> request.accountFrom
//    }
//    val article = when (request.type) {
//        TransactionType.OUTCOME -> request.articleTo
//        TransactionType.INCOME -> request.articleFrom
//        TransactionType.TRANSITION -> ARTICLE_OUTCOME_TRANSITION_SPECIAL_ID
//    }
//    return if (request.isScheduled) {
//        ScheduledTransactionInfo(request.type, request.sum, Date(),
//                TransactionScheduleTimeUnit.values()[request.period], accountId)
//    } else {
//        TransactionInfo(request.maxSum, article, Date(), accountId)
//    }
//}