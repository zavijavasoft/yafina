package com.zavijavasoft.yafina.ui.transactions

import com.arellomobile.mvp.MvpView
import com.zavijavasoft.yafina.model.TransactionInfo

interface TransactionsListView : MvpView {
    fun update(transactionsList: List<TransactionInfo>)
}