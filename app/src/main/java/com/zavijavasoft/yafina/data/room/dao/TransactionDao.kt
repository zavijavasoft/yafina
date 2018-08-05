package com.zavijavasoft.yafina.data.room.dao

import android.arch.persistence.room.*
import com.zavijavasoft.yafina.data.room.TransactionEntity

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction` WHERE id = :id")
    fun getTransactionById(id: Long): TransactionEntity

    @Query("SELECT * FROM `transaction`")
    fun getTransactions(): List<TransactionEntity>

    @Insert
    fun insertTransaction(transaction: TransactionEntity)

    @Delete
    fun deleteTransaction(transaction: TransactionEntity)

    @Update
    fun updateTransaction(transaction: TransactionEntity)
}