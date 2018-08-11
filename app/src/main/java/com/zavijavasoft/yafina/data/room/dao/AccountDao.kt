package com.zavijavasoft.yafina.data.room.dao

import android.arch.persistence.room.*
import com.zavijavasoft.yafina.model.AccountEntity

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    fun getAccounts(): List<AccountEntity>

    @Query("SELECT * FROM account WHERE id = :id")
    fun getAccountById(id: Long): AccountEntity

    @Insert
    fun insertAccount(accountEntity: AccountEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAccount(accountEntity: AccountEntity)

    @Delete
    fun deleteAccount(accountEntity: AccountEntity)
}