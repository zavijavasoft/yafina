package com.zavijavasoft.yafina.data.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity(tableName = "transaction")
data class TransactionEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val transactionId: Long,
        val sum: Float,
        @ColumnInfo(name = "article_id")
        val article: Long,
        @ColumnInfo(name = "account_id")
        val accountId: Long,
        val comment: String = ""
)

@Entity(tableName = "currency")
data class CurrencyEntity(
        @PrimaryKey
        val name: String
)