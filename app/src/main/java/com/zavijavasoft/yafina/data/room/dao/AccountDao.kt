package com.zavijavasoft.yafina.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.zavijavasoft.yafina.model.AccountEntity

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    fun getAccounts(): List<AccountEntity>

    @Query("SELECT * FROM account WHERE id = :id")
    fun getAccountById(id: Long): AccountEntity

    @Insert
    fun insertAccount(accountEntity: AccountEntity)
}