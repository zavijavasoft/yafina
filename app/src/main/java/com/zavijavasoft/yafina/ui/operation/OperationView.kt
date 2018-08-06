package com.zavijavasoft.yafina.ui.operation

import android.os.Parcelable
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
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
                              val isScheduled: Boolean,
                              val maxSum: Float,
                              val currency: String,
                              val accountFrom: Long,
                              val accountTo: Long,
                              val articleFrom: Long,
                              val articleTo: Long,
                              val day: Int,
                              val period: Int) : Parcelable

interface OperationView : MvpView {
    fun update(rests: Map<Long, Float>, arcticles: List<ArticleEntity>, accounts: List<AccountEntity>)
    fun notifyInsufficientMoney(accountTitle: String, sumExists: String, sumRequired: String)
    @StateStrategyType(SkipStrategy::class)
    fun requireTransaction(request: TransactionRequest)
}