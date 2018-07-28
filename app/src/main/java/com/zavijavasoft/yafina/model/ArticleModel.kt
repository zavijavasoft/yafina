package com.zavijavasoft.yafina.model

import rx.Single


enum class ArticleType {
    INCOME,
    OUTCOME
}


data class ArticleEntity(val articleId: Long, val type: ArticleType, val title: String, val description: String = "")

interface ArticlesStorage {
    fun getArticles(): Single<List<ArticleEntity>>
    fun getArticleById(id: Long): Single<ArticleEntity>
}