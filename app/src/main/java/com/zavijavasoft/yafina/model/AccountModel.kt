package com.zavijavasoft.yafina.model

import rx.Single


data class AccountEntity(val id: Long, val currency: String, val name: String, val description: String)


interface AccountsStorage {
    fun getAccounts(): Single<List<AccountEntity>>
    fun getAccountById(id: Long): Single<AccountEntity>
}