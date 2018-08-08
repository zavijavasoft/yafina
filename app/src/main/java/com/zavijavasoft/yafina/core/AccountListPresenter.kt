package com.zavijavasoft.yafina.core

interface AccountListPresenter {
    fun needUpdate()
    fun newAccount()
    fun edit(accountId: Long)
}