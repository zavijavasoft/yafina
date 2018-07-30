package com.zavijavasoft.yafina.ui.operation

import android.os.Parcelable
import com.arellomobile.mvp.MvpView
import com.zavijavasoft.yafina.model.AccountEntity
import com.zavijavasoft.yafina.model.ArticleEntity
import kotlinx.android.parcel.Parcelize

const val TRANSACTION_REQUEST_TAG = "TRANSACTION_REQUEST_TAG"

enum class TransactionType {
    INCOME,
    OUTCOME,
    TRANSITION
}

@Parcelize
data class TransactionRequest(val type: TransactionType,
                              val maxSum: Float,
                              val currency: String,
                              val accountFrom: Long,
                              val accountTo: Long,
                              val articleFrom: Long,
                              val articleTo: Long) : Parcelable

interface OperationView : MvpView {
    fun update(rests: Map<Long, Float>, arcticles: List<ArticleEntity>, accounts: List<AccountEntity>)
    fun notifyInsufficientMoney(accountTitle: String, sumExists: String, sumRequired: String)
    fun requireTransaction(request: TransactionRequest)
}