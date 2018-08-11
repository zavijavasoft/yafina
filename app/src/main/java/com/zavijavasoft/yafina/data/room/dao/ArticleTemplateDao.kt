package com.zavijavasoft.yafina.data.room.dao

import android.arch.persistence.room.*
import com.zavijavasoft.yafina.model.ArticleTemplateEntity

@Dao
interface ArticleTemplateDao {

    @Query("SELECT * FROM article_template WHERE article_id = :articleId")
    fun getTemplateByArticleId(articleId: Long): ArticleTemplateEntity

    @Insert
    fun insertTemplate(articleTemplate: ArticleTemplateEntity)

    @Insert
    fun insertTemplates(vararg articleTemplate: ArticleTemplateEntity)

    @Update
    fun updateTemplate(articleTemplate: ArticleTemplateEntity)

    @Delete
    fun deleteTemplate(articleTemplate: ArticleTemplateEntity)
}