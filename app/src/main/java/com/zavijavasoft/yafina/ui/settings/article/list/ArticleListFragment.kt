package com.zavijavasoft.yafina.ui.settings.article.list

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.model.ArticleEntity
import kotlinx.android.synthetic.main.fragment_article_list.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ArticleListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ArticleListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ArticleListFragment : MvpAppCompatFragment(), ArticleListView {

    @Inject
    @InjectPresenter
    lateinit var presenter: ArticleListPresenterImpl

    @ProvidePresenter
    fun providePresenter(): ArticleListPresenterImpl {
        return presenter
    }

    @Inject
    lateinit var appContext: Context

    private lateinit var listAdapter: ArticleListRecyclerViewAdapter
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_account_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_action_new -> {
                presenter.newArticle()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ArticleListRecyclerViewAdapter(mutableListOf()) { aId -> presenter.edit(aId) }
        rvArticles.adapter = adapter
        listAdapter = adapter

        rvArticles.addItemDecoration(DividerItemDecoration(activity, VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        presenter.needUpdate()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        YaFinaApplication.component.inject(this)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun update(articles: List<ArticleEntity>) {
        listAdapter.update(articles)
    }

    override fun requireArticleEdit(articleId: Long) {
        listener?.onEditArticle(articleId)
    }

    override fun requireArticleCreate() {
        listener?.onCreateArticle()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onEditArticle(articleId: Long)
        fun onCreateArticle()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ArticleListFragment.
         */
        fun newInstance() = ArticleListFragment()
    }
}
