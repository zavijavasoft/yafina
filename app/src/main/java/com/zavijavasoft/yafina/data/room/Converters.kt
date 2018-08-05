package com.zavijavasoft.yafina.data.room

import android.arch.persistence.room.TypeConverter
import com.zavijavasoft.yafina.model.ArticleType
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
}