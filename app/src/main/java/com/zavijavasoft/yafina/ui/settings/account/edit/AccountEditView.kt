package com.zavijavasoft.yafina.ui.settings.account.edit

import com.arellomobile.mvp.MvpView
import com.zavijavasoft.yafina.model.AccountEntity

interface AccountEditView: MvpView {
    fun updateCurrencies(currencies: List<String>)
    fun update(account: AccountEntity)
    fun close()
}