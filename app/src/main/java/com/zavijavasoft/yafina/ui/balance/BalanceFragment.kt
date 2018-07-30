package com.zavijavasoft.yafina.ui.balance

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.core.BalancePresenterImpl
import com.zavijavasoft.yafina.model.BalanceChunk
import javax.inject.Inject


class BalanceFragment : MvpAppCompatFragment(), BalanceView {

    companion object {
        const val TAG_YAFINA_BALANCE_FRAGMENT = "TAG_YAFINA_BALANCE_FRAGMENT"

        fun getInstance(): BalanceFragment {
            val fragment = BalanceFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var appContext: Context


    @Inject
    @InjectPresenter
    lateinit var presenter: BalancePresenterImpl

    @ProvidePresenter
    fun providePresenter(): BalancePresenterImpl {
        return presenter
    }

    @BindView(R.id.balance_rur)
    lateinit var rurSummary: TextView

    @BindView(R.id.balance_usd)
    lateinit var usdSummary: TextView

    @BindView(R.id.balance_update)
    lateinit var buttonUpdate: ImageButton


    @BindView(R.id.balance_recyclerview)
    lateinit var recyclerView: RecyclerView

    lateinit var adapter: BalanceAdapter

    lateinit var unbinder: Unbinder

    override fun onAttach(context: Context?) {
        YaFinaApplication.component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_balance, container, false)
        unbinder = ButterKnife.bind(this, view)


        adapter = BalanceAdapter(listOf(), appContext, presenter)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(appContext, LinearLayoutManager.HORIZONTAL, false)


        buttonUpdate.setOnClickListener {
            presenter.needUpdate()
        }


        return view
    }

    override fun onDestroyView() {
        unbinder.unbind()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        presenter.needUpdate()
    }

    override fun displayBalance(currency: String, sum: Float) {

        when (currency) {
            "USD" -> usdSummary.text = String.format("%.2f %s", sum, currency)
            "RUR" -> rurSummary.text = String.format("%.2f %s", sum, currency)
        }
    }

    override fun update(balances: List<BalanceChunk>) {
        adapter.update(balances)
    }
}
