package com.zavijavasoft.yafina.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import io.reactivex.Completable
import io.reactivex.Single

enum class ArticleType {
    INCOME,
    OUTCOME
}

@Entity(tableName = "article")
data class ArticleEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val articleId: Long,
        val type: ArticleType,
        val title: String,
        val description: String = ""
)

interface ArticlesStorage {
    fun getArticles(): Single<List<ArticleEntity>>
    fun getArticleById(id: Long): Single<ArticleEntity>
    fun addArticle(articleEntity: ArticleEntity): Completable
    fun updateArticle(article: ArticleEntity): Completable
}