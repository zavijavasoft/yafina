package com.zavijavasoft.yafina.ui.settings.article.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.ARTICLE_INCOME_TRANSITION_SPECIAL_ID
import com.zavijavasoft.yafina.model.ARTICLE_OUTCOME_TRANSITION_SPECIAL_ID
import com.zavijavasoft.yafina.model.FinanceTracker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class ArticleListPresenterImpl @Inject constructor(private val tracker: FinanceTracker)
    : MvpPresenter<ArticleListView>(), ArticleListPresenter {

    override fun newArticle() {
        viewState.requireArticleCreate()
    }

    override fun edit(articleId: Long) {
        viewState.requireArticleEdit(articleId)
    }

    override fun needUpdate() {
        tracker.getArticlesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    viewState.update(list.filter { it.articleId !in arrayOf(
                            ARTICLE_INCOME_TRANSITION_SPECIAL_ID,
                            ARTICLE_OUTCOME_TRANSITION_SPECIAL_ID)
                    })
                }
    }
}