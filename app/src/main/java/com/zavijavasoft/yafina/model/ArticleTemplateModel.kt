package com.zavijavasoft.yafina.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "article_template")
data class ArticleTemplateEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        @ColumnInfo(name = "article_id")
        val articleId: Long,
        @ColumnInfo(name = "is_scheduled")
        val isScheduled: Boolean,
        @ColumnInfo(name = "default_comment")
        val defaultComment: String,
        val period: TransactionScheduleTimeUnit = TransactionScheduleTimeUnit.WEEK
)

interface ArticleTemplateStorage {
    fun getTemplateByArticleId(articleId: Long): ArticleTemplateEntity
    fun insertTemplate(articleTemplate: ArticleTemplateEntity)
    fun deleteTemplate(articleTemplate: ArticleTemplateEntity)
    fun updateTemplate(articleTemplate: ArticleTemplateEntity)
}