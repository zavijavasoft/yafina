package com.zavijavasoft.yafina.ui.settings.account.list

interface AccountListPresenter {
    fun needUpdate()
    fun newAccount()
    fun edit(accountId: Long)
}