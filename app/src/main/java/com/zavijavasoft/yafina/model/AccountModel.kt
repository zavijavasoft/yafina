package com.zavijavasoft.yafina.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import io.reactivex.Completable
import io.reactivex.Single


@Entity(tableName = "account")
data class AccountEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val currency: String,
        val name: String,
        val description: String
)

interface AccountsStorage {
    fun getAccounts(): Single<List<AccountEntity>>
    fun getAccountById(id: Long): Single<AccountEntity>
    fun addAccount(account: AccountEntity): Completable
}