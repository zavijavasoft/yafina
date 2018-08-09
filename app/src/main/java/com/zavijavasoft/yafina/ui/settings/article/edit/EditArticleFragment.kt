package com.zavijavasoft.yafina.ui.settings.article.edit

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.model.ArticleEntity
import com.zavijavasoft.yafina.model.ArticleTemplateEntity
import com.zavijavasoft.yafina.model.ArticleType
import com.zavijavasoft.yafina.model.TransactionScheduleTimeUnit
import com.zavijavasoft.yafina.ui.settings.account.edit.EditAccountFragment
import kotlinx.android.synthetic.main.fragment_edit_article.*
import javax.inject.Inject

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ARTICLE_ID = "article-id"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditAccountFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EditAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EditArticleFragment : MvpAppCompatFragment(), ArticleEditView {

    @Inject
    @InjectPresenter
    lateinit var presenter: ArticleEditPresenterImpl

    @ProvidePresenter
    fun providePresenter(): ArticleEditPresenterImpl {
        return presenter
    }

    @Inject
    lateinit var appContext: Context

    private var articleId: Long = 0
    private var articleTemplateId: Long = 0
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        parseArgs()
    }

    private fun parseArgs() {
        arguments?.let {
            articleId = it.getLong(ARG_ARTICLE_ID)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_account_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_action_done -> {
                presenter.save(getArticle(), getTemplate())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getTemplate(): ArticleTemplateEntity {
        val isScheduled = chbx_is_scheduled.isChecked
        val period = TransactionScheduleTimeUnit.values()[spinnerTimeUnits.selectedItemPosition]
        val comment = etComment.text.toString()
        return ArticleTemplateEntity(articleTemplateId, articleId, isScheduled, comment, period)
    }

    private fun getArticle(): ArticleEntity {
        val id = articleId
        val name = etArticleTitle.text.toString()
        val articleType = ArticleType.values()[spinnerArticleType.selectedItemPosition]
        val description = etArticleDescription.text.toString()

        return ArticleEntity(id, articleType, name, description)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (articleId != 0L) {
            presenter.update(articleId)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        YaFinaApplication.component.inject(this)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun update(article: ArticleEntity, articleTemplate: ArticleTemplateEntity) {
        tilArticleTitle.isHintAnimationEnabled = false
        tilArticleDescription.isHintAnimationEnabled = false

        articleTemplateId = articleTemplate.id
        etArticleTitle.setText(article.title)
        etArticleDescription.setText(article.description)
        spinnerArticleType.setSelection(article.type.ordinal)

        tilArticleTitle.isHintAnimationEnabled = true
        tilArticleDescription.isHintAnimationEnabled = true

        etComment.setText(articleTemplate.defaultComment)
        chbx_is_scheduled.setOnCheckedChangeListener {_, isChecked ->
            spinnerTimeUnits.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
        }
        chbx_is_scheduled.isChecked = articleTemplate.isScheduled
        if (articleTemplate.isScheduled) {
            spinnerTimeUnits.setSelection(articleTemplate.period.ordinal)
        }
    }

    override fun close() {
        listener?.close()
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
        fun close()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment EditAccountFragment.
         */
        fun newInstance() = EditArticleFragment()

        fun newInstance(articleId: Long) = EditArticleFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_ARTICLE_ID, articleId)
            }
        }
    }
}
