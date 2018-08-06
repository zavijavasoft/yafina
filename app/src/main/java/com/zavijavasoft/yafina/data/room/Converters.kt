package com.zavijavasoft.yafina.data.room

import android.arch.persistence.room.TypeConverter
import com.zavijavasoft.yafina.model.ArticleType
import com.zavijavasoft.yafina.model.OneTimeTransactionInfo
import com.zavijavasoft.yafina.model.ScheduledTransactionInfo
import com.zavijavasoft.yafina.model.TransactionScheduleTimeUnit
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
                OneTimeTransactionEntity(ti.transactionId, ti.sum, ti.article, ti.accountId, ti.comment)

        fun toOneTimeTransactionInfo(te: OneTimeTransactionEntity) =
                OneTimeTransactionInfo(te.sum, te.article, Date(te.transactionId), te.accountId, te.comment)

        fun toScheduledTransactionEntity(ti: ScheduledTransactionInfo) =
                ScheduledTransactionEntity(ti.transactionId, ti.sum, ti.article, ti.accountId,
                        ti.comment, ti.period)

        fun toScheduledTransactionInfo(te: ScheduledTransactionEntity) =
                ScheduledTransactionInfo(te.sum, te.article, Date(te.transactionId), te.accountId,
                        te.comment, te.period)
    }
}