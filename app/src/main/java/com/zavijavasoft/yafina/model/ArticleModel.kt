package com.zavijavasoft.yafina.model

import io.reactivex.Single


const val ARTICLE_INCOME_TRANSITION_SPECIAL_ID = -1L
const val ARTICLE_OUTCOME_TRANSITION_SPECIAL_ID = -2L

enum class ArticleType {
    INCOME,
    OUTCOME
}


data class ArticleEntity(val articleId: Long, val type: ArticleType, val title: String, val description: String = "")

interface ArticlesStorage {
    fun getArticles(): Single<List<ArticleEntity>>
    fun getArticleById(id: Long): Single<ArticleEntity>
}