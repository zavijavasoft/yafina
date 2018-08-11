package com.zavijavasoft.yafina.data.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.zavijavasoft.yafina.model.TransactionScheduleTimeUnit

sealed class TransactionEntity

@Entity(tableName = "transaction")
data class OneTimeTransactionEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val transactionId: Long,
        @ColumnInfo(name = "transaction_type")
        val transactionType: Int,
        val sum: Float,
        @ColumnInfo(name = "account_to")
        val accountTo: Long,
        val comment: String,
        @ColumnInfo(name = "account_from")
        val accountFrom: Long,
        @ColumnInfo(name = "article_id")
        val article: Long
): TransactionEntity()

@Entity(tableName = "scheduled_transaction")
data class ScheduledTransactionEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val transactionId: Long,
        @ColumnInfo(name = "transaction_type")
        val transactionType: Int,
        val sum: Float,
        val period: TransactionScheduleTimeUnit,
        @ColumnInfo(name = "account_to")
        val accountTo: Long,
        val comment: String,
        @ColumnInfo(name = "account_from")
        val accountFrom: Long,
        @ColumnInfo(name = "article_id")
        val article: Long
): TransactionEntity()

@Entity(tableName = "currency")
data class CurrencyEntity(
        @PrimaryKey
        val name: String
)