package com.zavijavasoft.yafina.data.room.dao

import android.arch.persistence.room.*
import com.zavijavasoft.yafina.data.room.OneTimeTransactionEntity
import com.zavijavasoft.yafina.data.room.ScheduledTransactionEntity
import com.zavijavasoft.yafina.data.room.TransactionEntity

interface TransactionDao<T: TransactionEntity> {
    fun getTransactionById(id: Long): T
    fun getTransactions(): List<T>
    fun insertTransaction(transaction: T)
    fun deleteTransaction(transaction: T)
    fun updateTransaction(transaction: T)
    fun getTransactionsBetweenDate(dateFrom: Long, dateTo: Long): List<T>
}

@Dao
interface OneTimeTransactionDao: TransactionDao<OneTimeTransactionEntity> {

    @Query("SELECT * FROM `transaction` WHERE id = :id")
    override fun getTransactionById(id: Long): OneTimeTransactionEntity

    @Query("SELECT * FROM `transaction`")
    override fun getTransactions(): List<OneTimeTransactionEntity>

    @Query("SELECT * FROM `transaction` WHERE id BETWEEN :dateFrom AND :dateTo")
    override fun getTransactionsBetweenDate(dateFrom: Long, dateTo: Long): List<OneTimeTransactionEntity>

    @Insert
    override fun insertTransaction(transaction: OneTimeTransactionEntity)

    @Delete
    override fun deleteTransaction(transaction: OneTimeTransactionEntity)

    @Update
    override fun updateTransaction(transaction: OneTimeTransactionEntity)
}

@Dao
interface ScheduledTransactionDao: TransactionDao<ScheduledTransactionEntity> {

    @Query("SELECT * FROM scheduled_transaction WHERE id = :id")
    override fun getTransactionById(id: Long): ScheduledTransactionEntity

    @Query("SELECT * FROM scheduled_transaction")
    override fun getTransactions(): List<ScheduledTransactionEntity>

    @Query("SELECT * FROM scheduled_transaction WHERE id BETWEEN :dateFrom AND :dateTo")
    override fun getTransactionsBetweenDate(dateFrom: Long, dateTo: Long): List<ScheduledTransactionEntity>

    @Insert
    override fun insertTransaction(transaction: ScheduledTransactionEntity)

    @Delete
    override fun deleteTransaction(transaction: ScheduledTransactionEntity)

    @Update
    override fun updateTransaction(transaction: ScheduledTransactionEntity)
}