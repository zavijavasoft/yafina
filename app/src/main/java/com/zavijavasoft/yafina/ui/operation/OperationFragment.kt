package com.zavijavasoft.yafina.ui.operation


import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.model.AccountEntity
import com.zavijavasoft.yafina.model.ArticleEntity
import com.zavijavasoft.yafina.model.ArticleType
import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.ui.edittransaction.EditTransactionActivity
import com.zavijavasoft.yafina.utils.ColorSelector
import javax.inject.Inject


class OperationFragment : MvpAppCompatFragment(), OperationView {

    companion object {
        const val TAG_YAFINA_OPERATION_FRAGMENT = "TAG_YAFINA_OPERATION_FRAGMENT"

        fun getInstance(): OperationFragment {
            val fragment = OperationFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    @Inject
    @InjectPresenter
    lateinit var presenter: OperationPresenterImpl

    @ProvidePresenter
    fun providePresenter(): OperationPresenterImpl {
        return presenter
    }

    @Inject
    lateinit var appContext: Context


    @BindView(R.id.recycler_view_income)
    lateinit var recyclerIncome: RecyclerView
    @BindView(R.id.recycler_view_outcome)
    lateinit var recyclerOutcome: RecyclerView
    @BindView(R.id.recycler_view_accounts)
    lateinit var recyclerAccounts: RecyclerView

    private lateinit var accountAdapter: AccountAdapter
    private lateinit var incomeAdapter: ArticleAdapter
    private lateinit var outcomeAdapter: ArticleAdapter


    lateinit var unbinder: Unbinder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        accountAdapter = AccountAdapter(listOf(), appContext, presenter)
        incomeAdapter = ArticleAdapter(listOf(), appContext, presenter)
        outcomeAdapter = ArticleAdapter(listOf(), appContext, presenter)

        val view = inflater.inflate(R.layout.fragment_operation, container, false)
        unbinder = ButterKnife.bind(this, view)

        recyclerIncome.tag = INCOME_ARTICLE_RECYCLER_VIEW_TAG
        recyclerIncome.adapter = incomeAdapter
        recyclerIncome.layoutManager = LinearLayoutManager(appContext, LinearLayoutManager.HORIZONTAL, false)

        recyclerOutcome.tag = OUTCOME_ARTICLE_RECYCLER_VIEW_TAG
        recyclerOutcome.adapter = outcomeAdapter
        recyclerOutcome.layoutManager = LinearLayoutManager(appContext, LinearLayoutManager.HORIZONTAL, false)

        recyclerAccounts.tag = ACCOUNTS_RECYCLER_VIEW_TAG
        recyclerAccounts.adapter = accountAdapter
        recyclerAccounts.layoutManager = LinearLayoutManager(appContext, LinearLayoutManager.HORIZONTAL, false)


        return view
    }


    override fun onAttach(context: Context?) {
        YaFinaApplication.component.inject(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        presenter.needUpdate()
    }


    override fun onDestroyView() {
        unbinder.unbind()
        super.onDestroyView()
    }


    override fun update(rests: Map<Long, Float>, arcticles: List<ArticleEntity>, accounts: List<AccountEntity>) {

        val accountsList = mutableListOf<OperationAccountItem>()

        for (account in accounts) {

            val textSum = String.format("%.2f %s", rests[account.id] ?: 0.0f, account.currency)

            val color = ColorSelector.getColorByLeadingLetter(account.name[0])
            accountsList.add(OperationAccountItem(account.id, account.name, textSum, color))
        }

        accountAdapter.update(accountsList)

        val incomeArticlesList = mutableListOf<OperationArticleItem>()
        val outcomeArticlesList = mutableListOf<OperationArticleItem>()
        for (article in arcticles) {
            val color = ColorSelector.getColorByLeadingLetter(article.title[0])
            when (article.type) {
                ArticleType.INCOME ->
                    incomeArticlesList.add(OperationArticleItem(article.articleId, article.title, article.type, color))
                ArticleType.OUTCOME ->
                    outcomeArticlesList.add(OperationArticleItem(article.articleId, article.title, article.type, color))

            }
        }

        incomeAdapter.update(incomeArticlesList)
        outcomeAdapter.update(outcomeArticlesList)

    }

    override fun requireTransaction(transaction: TransactionInfo) {
        startActivity(EditTransactionActivity.newIntent(context, transaction, true))
    }
}
