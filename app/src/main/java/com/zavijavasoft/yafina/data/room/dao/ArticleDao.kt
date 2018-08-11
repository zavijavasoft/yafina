package com.zavijavasoft.yafina.data.room.dao

import android.arch.persistence.room.*
import com.zavijavasoft.yafina.model.ArticleEntity

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getArticles(): List<ArticleEntity>

    @Query("SELECT * FROM article WHERE id = :id")
    fun getArticleById(id: Long): ArticleEntity

    @Insert
    fun insertArticle(articleEntity: ArticleEntity): Long

    @Insert
    fun insertArticles(vararg articleEntities: ArticleEntity)

    @Update
    fun updateArticle(articleEntity: ArticleEntity)

    @Delete
    fun deleteArticle(articleEntity: ArticleEntity)
}