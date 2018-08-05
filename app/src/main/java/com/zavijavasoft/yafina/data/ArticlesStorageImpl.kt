package com.zavijavasoft.yafina.data

import com.zavijavasoft.yafina.data.room.dao.ArticleDao
import com.zavijavasoft.yafina.model.ArticleEntity
import com.zavijavasoft.yafina.model.ArticlesStorage
import io.reactivex.Single
import javax.inject.Inject


class ArticlesStorageImpl
    @Inject constructor(private val dao: ArticleDao)
    : ArticlesStorage {

    override fun getArticles(): Single<List<ArticleEntity>> {
        return Single.just(dao.getArticles()
                .sortedBy { it.articleId }
        )
    }

    override fun getArticleById(id: Long): Single<ArticleEntity> {
        return Single.just(dao.getArticleById(id))
    }

//    override fun addArticle(articleEntity: ArticleEntity): Completable {
//        return Completable.fromAction {
//            dao.insertArticle(articleEntity)
//        }
//    }
}