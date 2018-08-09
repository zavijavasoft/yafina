package com.zavijavasoft.yafina.ui.edittransaction

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.DividerItemDecoration
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.model.*
import com.zavijavasoft.yafina.utils.TransactionType
import com.zavijavasoft.yafina.utils.getOwnerAccount
import kotlinx.android.synthetic.main.activity_edit_transaction.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

private const val ARG_TRANSACTION = "transaction"
private const val ARG_IS_NEW = "is-new"

class EditTransactionActivity : MvpAppCompatActivity(), EditTransactionView {

    @InjectPresenter
    @Inject
    lateinit var presenter: EditTransactionPresenterImpl

    @ProvidePresenter
    fun providePresenter(): EditTransactionPresenterImpl {
        return presenter
    }

    @Inject
    lateinit var appContext: Context

    private lateinit var transaction: TransactionInfo
    private var isNew: Boolean = false
    private lateinit var calendar: Calendar
    private lateinit var currentSum: String
    private lateinit var currentCurrency: String
    private var currentAccountId: Long = -1

    private lateinit var listAdapter: SelectionListRecyclerListAdapter
    private lateinit var currencyArrayAdapter: ArrayAdapter<String>
    private lateinit var accountArrayAdapter: ArrayAdapter<Pair<Long, String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        YaFinaApplication.component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_transaction)

        parseArgs()
        initFields()
    }

    private fun parseArgs() {
        intent.let {
            transaction = it.getParcelableExtra(ARG_TRANSACTION)
            isNew = it.getBooleanExtra(ARG_IS_NEW, false)
        }
    }

    private fun initFields() {
        initRecyclerView()
        initSelectionTabLayout()
        initDateButton()
        initSumEditor()
        initAccountSection()
        initCurrencyButton()
        initScheduleSection()
        etComment.setText(transaction.comment)
    }

    private fun initScheduleSection() {
        chbx_is_scheduled.setOnCheckedChangeListener {_, isChecked ->
            spinnerTimeUnits.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
        }
        if (transaction is ScheduledTransactionInfo) {
            chbx_is_scheduled.isChecked = true
            spinnerTimeUnits.setSelection((transaction as ScheduledTransactionInfo).period.ordinal)
        }
    }

    private fun initCurrencyButton() {
        presenter.getCurrencies()
        btnCurrency.setOnClickListener {
            showDialog("Choose currency", currencyArrayAdapter) { position ->
                currentCurrency = currencyArrayAdapter.getItem(position)
                btnCurrency.text = currentCurrency
            }
        }
    }

    private fun initAccountSection() {
        currentAccountId = getOwnerAccount(transaction)
        presenter.getAccount(currentAccountId)
        presenter.getAccounts()
        btnCurrentAccount.setOnClickListener {
            showDialog("Choose account", accountArrayAdapter) { position ->
                val account = accountArrayAdapter.getItem(position)
                currentAccountId = account.first
                btnCurrentAccount.text = account.second
            }
        }
    }

    private fun initSumEditor() {
        currentSum = transaction.sum.toString()
        etSum.addTextChangedListener(object: TextWatcher {

            private val pattern: Pattern = Pattern.compile("^\\d{1,15}([.,]\\d{0,2})?$")

            private fun isValid(s: CharSequence) = pattern.matcher(s).matches()

            override fun afterTextChanged(s: Editable) {
                if (!isValid(s.toString())) {
                    etSum.removeTextChangedListener(this)
                    etSum.setText(currentSum)
                    etSum.setSelection(currentSum.length)
                    etSum.addTextChangedListener(this)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val s = p0.toString()
                currentSum = if (isValid(s)) s else currentSum
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        etSum.setText(currentSum)
        etSum.setSelection(currentSum.length)
    }

    private fun initDateButton() {
        calendar = Calendar.getInstance()
        calendar.timeInMillis = transaction.transactionId
        showSelectedDate()
        btnDate.setOnClickListener {
            showDateDialog()
        }
    }

    private fun initSelectionTabLayout() {
        tabTransactionType.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    TransactionType.INCOME.ordinal -> {
                        presenter.getIncomingsList()
                    }
                    TransactionType.OUTCOME.ordinal -> {
                        presenter.getOutgoingsList()
                    }
                    TransactionType.TRANSITION.ordinal -> {
                        presenter.getAccountsExcept(getOwnerAccount(transaction))
                    }
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })

        val tab = tabTransactionType.getTabAt(transaction.transactionType.ordinal)
        if (tab?.isSelected == true) {
            presenter.getIncomingsList()
        } else {
            tab?.select()
        }
    }

    private fun initRecyclerView() {
        listAdapter = SelectionListRecyclerListAdapter(mutableListOf())
        rvItems.adapter = listAdapter

        rvItems.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        if (isNew) {
            menu.getItem(0).isVisible = false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_action_done -> {
                saveTransaction()
            }
            R.id.mi_action_delete -> {
                deleteTransaction()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveTransaction() {
        val updatedTransaction = parseTransaction()
        if (updatedTransaction != null) {
            presenter.saveTransaction(updatedTransaction, isNew)
            return
        }
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
    }

    private fun deleteTransaction() {
        presenter.deleteTransaction(transaction)
    }

    private fun parseTransaction(): TransactionInfo? {
        if (listAdapter.getLastSelectedItem() == null)
            return null
        val type = TransactionType.values()[tabTransactionType.selectedTabPosition]
        val sum = etSum.text.toString().toFloat()

        val date = calendar.time
        val accountTo = when (type) {
            TransactionType.INCOME -> currentAccountId
            TransactionType.OUTCOME -> 0
            TransactionType.TRANSITION -> listAdapter.getLastSelectedItem()?.id!!
        }
        val accountIdFrom = when (type) {
            TransactionType.INCOME -> 0
            else -> currentAccountId
        }
        val comment = etComment.text.toString()
        val articleId = when (type) {
            TransactionType.TRANSITION -> 0
            else -> listAdapter.getLastSelectedItem()?.id!!
        }
        val isScheduled = chbx_is_scheduled.isChecked
        val period = TransactionScheduleTimeUnit.values()[spinnerTimeUnits.selectedItemPosition]

        return if (isScheduled)
            ScheduledTransactionInfo(type, sum, date, period, accountTo, comment, accountIdFrom, articleId)
        else OneTimeTransactionInfo(type, sum, date, accountTo, comment, accountIdFrom, articleId)
    }

    override fun setAccounts(accounts: List<AccountEntity>, exceptOne: Boolean) {
        listAdapter.update(accounts.map { SelectionItem(it.id, it.name) })
    }

    override fun setAccounts(accounts: List<AccountEntity>) {
        accountArrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        accountArrayAdapter.addAll(accounts.map { it.id to it.name })
    }

    override fun setIncomings(incomings: List<ArticleEntity>) {
        listAdapter.update(incomings.map { SelectionItem(it.articleId, it.title) })
        val article = incomings.find { it.articleId == transaction.articleId }
        if (article != null) {
            listAdapter.setSelected(incomings.indexOf(article))
        }
    }

    override fun setOutgoings(outgoings: List<ArticleEntity>) {
        listAdapter.update(outgoings.map { SelectionItem(it.articleId, it.title) })
        val article = outgoings.find { it.articleId == transaction.articleId }
        if (article != null) {
            listAdapter.setSelected(outgoings.indexOf(article))
        }
    }

    override fun close() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun setAccount(account: AccountEntity) {
        btnCurrentAccount.text = account.name
        currentCurrency = account.currency
        btnCurrency.text = currentCurrency
    }

    override fun setCurrencies(currencies: List<String>) {
        currencyArrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        currencyArrayAdapter.addAll(currencies)
    }

    private fun showDateDialog() {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val dateChangeCallback = DatePickerDialog.OnDateSetListener { _, y, m, d ->
            calendar.set(y, m, d)
            showSelectedDate()
        }
        val dpk = DatePickerDialog(this, dateChangeCallback, year, month, day)
        dpk.show()
    }

    private fun showSelectedDate() {
        btnDate.text = SimpleDateFormat.getDateInstance().format(calendar.time)
    }

    private fun showDialog(title: String, adapter: ArrayAdapter<*>, onClickListener: (Int) -> Unit) {
        AlertDialog.Builder(this)
                .setTitle(title)
                .setAdapter(adapter) { dialog, which ->
                    onClickListener.invoke(which)
                    dialog.dismiss()
                }.show()
    }

    companion object {

        fun newIntent(context: Context?, transaction: TransactionInfo, isNew: Boolean = false) =
                Intent(context, EditTransactionActivity::class.java).apply {
                    putExtra(ARG_TRANSACTION, transaction)
                    putExtra(ARG_IS_NEW, isNew)
                }
    }
}
