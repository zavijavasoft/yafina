package com.zavijavasoft.yafina.data.room

import android.arch.persistence.room.TypeConverter
import com.zavijavasoft.yafina.model.ArticleType
import com.zavijavasoft.yafina.model.OneTimeTransactionInfo
import com.zavijavasoft.yafina.model.ScheduledTransactionInfo
import com.zavijavasoft.yafina.model.TransactionScheduleTimeUnit
import com.zavijavasoft.yafina.utils.TransactionType
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

    @TypeConverter
    fun toTransactionScheduleTimeUnit(ordinal: Int) = TransactionScheduleTimeUnit.values()[ordinal]

    @TypeConverter
    fun toOrdinal(timeUnit: TransactionScheduleTimeUnit) = timeUnit.ordinal

    companion object {
        fun toOneTimeTransactionEntity(ti: OneTimeTransactionInfo) =
                OneTimeTransactionEntity(ti.transactionId, ti.transactionType.ordinal, ti.sum, ti.accountIdTo,
                        ti.comment, ti.accountIdFrom, ti.articleId)

        fun toTransactionInfo(te: OneTimeTransactionEntity) =
                OneTimeTransactionInfo(TransactionType.values()[te.transactionType], te.sum,
                        Date(te.transactionId), te.accountTo, te.comment, te.accountFrom, te.article)

        fun toScheduledTransactionEntity(ti: ScheduledTransactionInfo) =
                ScheduledTransactionEntity(ti.transactionId, ti.transactionType.ordinal, ti.sum,
                        ti.period, ti.accountIdTo, ti.comment, ti.accountIdFrom, ti.articleId)

        fun toScheduledTransactionInfo(te: ScheduledTransactionEntity) =
                ScheduledTransactionInfo(TransactionType.values()[te.transactionType], te.sum,
                        Date(te.transactionId), te.period, te.accountTo,
                        te.comment, te.accountFrom, te.article)
    }
}