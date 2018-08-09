package com.zavijavasoft.yafina.ui.transactions

import com.arellomobile.mvp.MvpView
import com.zavijavasoft.yafina.model.AccountEntity
import com.zavijavasoft.yafina.model.ArticleEntity
import com.zavijavasoft.yafina.model.TransactionInfo

interface TransactionsListView : MvpView {
    fun update(res: List<Triple<TransactionInfo, ArticleEntity, AccountEntity>>)
}