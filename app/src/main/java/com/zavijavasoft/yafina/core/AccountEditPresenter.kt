package com.zavijavasoft.yafina.core

import com.zavijavasoft.yafina.model.AccountEntity

interface AccountEditPresenter {
    fun update()
    fun update(accountId: Long)
    fun save(account: AccountEntity)
}