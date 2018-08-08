package com.zavijavasoft.yafina.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.ImageButton
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.ui.balance.BalanceFragment
import com.zavijavasoft.yafina.ui.operation.OperationFragment
import com.zavijavasoft.yafina.ui.settings.SettingsFragment
import com.zavijavasoft.yafina.ui.transactions.TransactionsFragment

class YaFinaActivity : MvpAppCompatActivity(), MainView {


    @BindView(R.id.image_button_balance)
    lateinit var buttonBalance: ImageButton

    @BindView(R.id.image_button_operation)
    lateinit var buttonOperation: ImageButton


    @BindView(R.id.image_button_transactions)
    lateinit var buttonTransactionsList: ImageButton

    @BindView(R.id.image_button_settings)
    lateinit var buttonSettings: ImageButton

    @BindView(R.id.image_button_about)
    lateinit var buttonAbout: ImageButton


    @InjectPresenter
    lateinit var mainPresenter: MainPresenterImpl


    lateinit var unbinder: Unbinder

    var tabState: MainTabs = MainTabs.BALANCE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

        unbinder = ButterKnife.bind(this)

        if (null == savedInstanceState &&
                null == supportFragmentManager.findFragmentByTag(BalanceFragment.TAG_YAFINA_BALANCE_FRAGMENT)) {
            val balanceFragment = BalanceFragment.getInstance()
            supportFragmentManager.beginTransaction()
                    .add(R.id.balance_container,
                            balanceFragment, BalanceFragment.TAG_YAFINA_BALANCE_FRAGMENT)
                    .commit()

        }


        initNavigationButtons()
    }

    // Код, который вызовется раз в тысячу лет. Но симметрия должна соблюдаться
    override fun onDestroy() {
        unbinder.unbind()
        super.onDestroy()
    }


    private fun initSingleNavigationButton(imageButton: ImageButton, tag: String, newFragment: Fragment) {
        imageButton.setOnClickListener {
            val fragment = supportFragmentManager
                    .findFragmentByTag(tag)
            if (fragment == null) {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.balance_container, newFragment, tag)
                        .commit()

            }
        }
    }

    private fun initNavigationButtons() {

        initSingleNavigationButton(buttonBalance, BalanceFragment.TAG_YAFINA_BALANCE_FRAGMENT, BalanceFragment.getInstance())
        initSingleNavigationButton(buttonOperation, OperationFragment.TAG_YAFINA_OPERATION_FRAGMENT, OperationFragment.getInstance())
        initSingleNavigationButton(buttonTransactionsList, TransactionsFragment.TAG_YAFINA_TRANSACTION_FRAGMENT, TransactionsFragment.getInstance())
        initSingleNavigationButton(buttonSettings, SettingsFragment.TAG_YAFINA_SETTINGS_FRAGMENT, SettingsFragment.getInstance())
        initSingleNavigationButton(buttonAbout, AboutFragment.TAG_YAFINA_ABOUT_FRAGMENT, AboutFragment.getInstance())
    }

    override fun switchNewTab(tab: MainTabs) {
        tabState = tab

    }

}
