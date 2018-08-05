package com.zavijavasoft.yafina.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.zavijavasoft.yafina.data.room.AppDatabase
import com.zavijavasoft.yafina.data.room.dao.ArticleDao
import com.zavijavasoft.yafina.model.ARTICLE_INCOME_TRANSITION_SPECIAL_ID
import com.zavijavasoft.yafina.model.ARTICLE_OUTCOME_TRANSITION_SPECIAL_ID
import com.zavijavasoft.yafina.model.ArticleEntity
import com.zavijavasoft.yafina.model.ArticleType
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test

class ArticleDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: ArticleDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getArticleDao()
    }

    @After
    fun tearDown() {
        db.clearAllTables()
    }

    @Test
    fun test_addAccount() {
        val expected = ArticleEntity(ARTICLE_INCOME_TRANSITION_SPECIAL_ID, ArticleType.INCOME, "Скрытая (входящий для переводов между счетами)", "")

        dao.insertArticle(expected)

        val actual = dao.getArticleById(expected.articleId)
        TestCase.assertEquals(expected, actual)
    }

    @Test
    fun test_addArticleList() {
        val expected = listOf(
                ArticleEntity(ARTICLE_OUTCOME_TRANSITION_SPECIAL_ID, ArticleType.OUTCOME,
                        "Скрытая (исходящая для переводов между счетами)", ""),
                ArticleEntity(ARTICLE_INCOME_TRANSITION_SPECIAL_ID, ArticleType.INCOME,
                        "Скрытая (входящий для переводов между счетами)", ""),
                ArticleEntity(1, ArticleType.OUTCOME,
                        "Продукты", "То, что едят"),
                ArticleEntity(2, ArticleType.OUTCOME,
                        "Одежда", "То, что надевают")
        )
        expected.forEach { dao.insertArticle(it) }

        val actual = dao.getArticles()
        TestCase.assertEquals(expected, actual)
    }
}