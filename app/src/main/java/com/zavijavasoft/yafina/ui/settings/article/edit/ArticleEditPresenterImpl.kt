package com.zavijavasoft.yafina.ui.settings.article.edit

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.ArticleEntity
import com.zavijavasoft.yafina.model.ArticleTemplateEntity
import com.zavijavasoft.yafina.model.ArticleTemplateStorage
import com.zavijavasoft.yafina.model.ArticlesStorage
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class ArticleEditPresenterImpl @Inject constructor(
        private val articlesStorage: ArticlesStorage,
        private val articleTemplateStorage: ArticleTemplateStorage
        ) : MvpPresenter<ArticleEditView>(), ArticleEditPresenter {

    override fun update(articleId: Long) {
        Single.zip(
                articlesStorage.getArticleById(articleId),
                Single.fromCallable {articleTemplateStorage.getTemplateByArticleId(articleId)},
                BiFunction { article: ArticleEntity, articleTemplate: ArticleTemplateEntity ->
                    article to articleTemplate
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { (article, template) ->
                    viewState.update(article, template)
                }
    }

    override fun save(article: ArticleEntity, template: ArticleTemplateEntity) {
        Completable.fromAction {
            if (article.articleId == 0L) {
                val id = articlesStorage.addArticle(article).blockingGet()
                articleTemplateStorage.insertTemplate(template.copy(articleId = id))
            } else {
                articlesStorage.updateArticle(article).blockingAwait()
                articleTemplateStorage.updateTemplate(template)
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewState.close()
                }
    }

    override fun delete(article: ArticleEntity, template: ArticleTemplateEntity) {
        Completable.fromAction {
            articleTemplateStorage.deleteTemplate(template)
            articlesStorage.deleteArticle(article)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewState.close()
                }
    }
}