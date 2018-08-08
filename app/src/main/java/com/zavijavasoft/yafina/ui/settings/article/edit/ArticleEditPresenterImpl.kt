package com.zavijavasoft.yafina.ui.settings.article.edit

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.ArticleEntity
import com.zavijavasoft.yafina.model.ArticlesStorage
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class ArticleEditPresenterImpl @Inject constructor(
        private val articlesStorage: ArticlesStorage
        ) : MvpPresenter<ArticleEditView>(), ArticleEditPresenter {

    override fun update(articleId: Long) {
        articlesStorage.getArticleById(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { article ->
                    viewState.update(article)
                }
    }

    override fun save(article: ArticleEntity) {
        val result: Completable = if (article.articleId == 0L) {
            articlesStorage.addArticle(article)
        } else {
            articlesStorage.updateArticle(article)
        }
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewState.close()
                }
    }
}