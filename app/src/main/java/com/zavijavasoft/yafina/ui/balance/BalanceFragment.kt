package com.zavijavasoft.yafina.ui.balance

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.ButterKnife
import butterknife.Unbinder
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.YaFinaApplication
import kotlinx.android.synthetic.main.fragment_balance.*
import java.text.SimpleDateFormat
import java.util.*
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

    lateinit var unbinder: Unbinder

    private lateinit var chartAdapter: ChartRecyclerViewAdapter
    private var dateFrom = Date(0)
    private var dateTo = Date()

    override fun onAttach(context: Context?) {
        YaFinaApplication.component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_balance, container, false)
        unbinder = ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        chartAdapter = ChartRecyclerViewAdapter(mutableListOf())
        rvCharts.adapter = chartAdapter

        btnDateFrom.setOnClickListener { showDateDialog(btnDateFrom, dateFrom) }
        showSelectedDate(btnDateFrom, dateFrom)
        btnDateTo.setOnClickListener { showDateDialog(btnDateTo, dateTo) }
        showSelectedDate(btnDateTo, dateTo)
    }

    override fun onDestroyView() {
        unbinder.unbind()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        requestUpdate()
    }

    private fun requestUpdate() {
        chartAdapter.clear()
        rvCharts.removeAllViews()
        rvCharts.recycledViewPool.clear()
        presenter.needUpdatePieCurrenciesBetween(dateFrom, dateTo)
        presenter.needUpdateBarSpendingBetween(dateFrom, dateTo)
    }

    override fun updateBarSpending(data: MutableList<IBarDataSet>) {
        val chart = BarChart(context)
        val barData = BarData(data)
        chart.data = barData
        chart.description.isEnabled = false
        chartAdapter.add(chart)
    }

    override fun updateBarCurrencies(data: MutableList<IBarDataSet>) {
        val chart = BarChart(context)
        val barData = BarData(data)
        chart.data = barData
        chart.description.isEnabled = false
        chartAdapter.add(chart)
    }

    private fun showDateDialog(btnDate: Button, date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val dateChangeCallback = DatePickerDialog.OnDateSetListener { _, y, m, d ->
            calendar.set(y, m, d)
            val dt = calendar.time
            showSelectedDate(btnDate, dt)
            if (btnDate == btnDateFrom) {
                dateFrom = dt
            } else {
                dateTo = dt
            }
            requestUpdate()
        }
        val dpk = DatePickerDialog(context, dateChangeCallback, year, month, day)
        dpk.show()
    }

    private fun showSelectedDate(btnDate: Button, date: Date) {
        btnDate.text = SimpleDateFormat.getDateInstance().format(date)
    }
}
