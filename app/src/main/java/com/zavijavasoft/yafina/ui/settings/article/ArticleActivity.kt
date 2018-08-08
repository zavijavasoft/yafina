package com.zavijavasoft.yafina.ui.settings.article

import android.os.Bundle
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.ui.settings.article.edit.EditArticleFragment
import com.zavijavasoft.yafina.ui.settings.article.list.ArticleListFragment

class ArticleActivity : MvpAppCompatActivity(),
        ArticleListFragment.OnFragmentInteractionListener,
        EditArticleFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts)

        showArticleList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val fm = supportFragmentManager
                if (fm.backStackEntryCount > 1) {
                    fm.popBackStack()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onEditArticle(articleId: Long) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, EditArticleFragment.newInstance(articleId))
                .commit()
    }

    override fun onCreateArticle() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, EditArticleFragment.newInstance())
                .commit()
    }

    override fun close() {
        showArticleList()
    }

    private fun showArticleList() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, ArticleListFragment.newInstance())
                .commit()
    }
}
