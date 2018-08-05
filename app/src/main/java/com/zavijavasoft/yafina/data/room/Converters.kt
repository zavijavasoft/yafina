package com.zavijavasoft.yafina.data.room

import android.arch.persistence.room.TypeConverter
import com.zavijavasoft.yafina.model.ArticleType
import com.zavijavasoft.yafina.model.TransactionInfo
import java.util.*

class Converters {

    @TypeConverter
    fun toArticleType(ordinal: Int) = ArticleType.values()[ordinal]

    @TypeConverter
    fun toOrdinal(articleType: ArticleType) = articleType.ordinal

    @TypeConverter
    fun toDate(time: Long) = Date(time)

    @TypeConverter
    fun toTime(date: Date) = date.time

    companion object {
        fun toTransactionEntity(ti: TransactionInfo) =
                TransactionEntity(ti.transactionId, ti.sum, ti.article, ti.accountId, ti.comment)

        fun toTransactionInfo(te: TransactionEntity) =
                TransactionInfo(te.sum, te.article, Date(te.transactionId), te.accountId, te.comment)
    }
}