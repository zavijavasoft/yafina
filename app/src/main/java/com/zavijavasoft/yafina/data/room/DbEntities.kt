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
        val sum: Float,
        @ColumnInfo(name = "article_id")
        val article: Long,
        @ColumnInfo(name = "account_id")
        val accountId: Long,
        val comment: String = ""
): TransactionEntity()

@Entity(tableName = "scheduled_transaction")
data class ScheduledTransactionEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val transactionId: Long,
        val sum: Float,
        @ColumnInfo(name = "article_id")
        val article: Long,
        @ColumnInfo(name = "account_id")
        val accountId: Long,
        val comment: String = "",
        val period: TransactionScheduleTimeUnit
): TransactionEntity()

@Entity(tableName = "currency")
data class CurrencyEntity(
        @PrimaryKey
        val name: String
)