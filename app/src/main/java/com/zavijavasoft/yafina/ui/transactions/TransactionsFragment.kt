package com.zavijavasoft.yafina.ui.transactions

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
import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.ui.edittransaction.EditTransactionActivity
import javax.inject.Inject


class TransactionsFragment : MvpAppCompatFragment(), TransactionsListView {

    companion object {
        const val TAG_YAFINA_TRANSACTION_FRAGMENT = "TAG_YAFINA_TRANSACTION_FRAGMENT"

        fun getInstance(): TransactionsFragment {
            val fragment = TransactionsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    @Inject
    @InjectPresenter
    lateinit var presenter: TransactionsListPresenterImpl

    @ProvidePresenter
    fun providePresenter(): TransactionsListPresenterImpl {
        return presenter
    }

    @Inject
    lateinit var appContext: Context


    @BindView(R.id.transactions_recycler_view)
    lateinit var recyclerView: RecyclerView

    lateinit var adapter: TransactionsListViewAdapter

    lateinit var unbinder: Unbinder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transactions, container, false)
        unbinder = ButterKnife.bind(this, view)

        adapter = TransactionsListViewAdapter(listOf(), appContext, presenter,
                object: TransactionsListViewAdapter.OnClickListener {
                    override fun onEdit(transaction: TransactionInfo) {
                        startActivity(EditTransactionActivity.newIntent(context, transaction))
                    }
                })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(appContext, LinearLayoutManager.VERTICAL, false)

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


    override fun update(res: List<Triple<TransactionInfo, ArticleEntity, AccountEntity>>) {
        adapter.update(res)
    }
}
