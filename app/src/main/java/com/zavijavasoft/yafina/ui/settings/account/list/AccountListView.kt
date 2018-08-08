package com.zavijavasoft.yafina.ui.settings.account.list

import com.arellomobile.mvp.MvpView
import com.zavijavasoft.yafina.model.AccountEntity

interface AccountListView : MvpView {
    fun update(accounts: List<AccountEntity>)
    fun requireAccountEdit(accountId: Long)
    fun requireAccountCreate()
}