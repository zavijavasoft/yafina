package com.zavijavasoft.yafina


import android.os.Bundle
import android.widget.ImageButton
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.zavijavasoft.yafina.core.MainPresenterImpl
import com.zavijavasoft.yafina.ui.*

class YaFinaActivity : MvpAppCompatActivity(), MainView {


    @BindView(R.id.image_button_balance)
    lateinit var buttonBalance: ImageButton

    // @BindView(R.id.image_button_transactions)
    lateinit var buttonTransacionsList: ImageButton

    @BindView(R.id.image_button_settings)
    lateinit var buttonSettings: ImageButton

    @BindView(R.id.image_button_about)
    lateinit var buttonAbout: ImageButton

    @InjectPresenter
    lateinit var mainPresenter: MainPresenterImpl


    lateinit var unbinder: Unbinder

    var tabState: MainTabs = MainTabs.BALANCE





    override fun onCreate(savedInstanceState: Bundle?) {
        //AndroidInjection.inject(this)
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

    private fun initNavigationButtons() {

        buttonBalance.setOnClickListener {
            val fragment = supportFragmentManager
                    .findFragmentByTag(BalanceFragment.TAG_YAFINA_BALANCE_FRAGMENT)
            if (fragment == null) {
                val balanceFragment = BalanceFragment.getInstance()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.balance_container, balanceFragment,
                                BalanceFragment.TAG_YAFINA_BALANCE_FRAGMENT)
                        .commit()

            }
        }

        buttonSettings.setOnClickListener {
            val fragment = supportFragmentManager
                    .findFragmentByTag(SettingsFragment.TAG_YAFINA_SETTINGS_FRAGMENT)
            if (fragment == null) {
                val settingsFragment = SettingsFragment.getInstance()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.balance_container, settingsFragment,
                                SettingsFragment.TAG_YAFINA_SETTINGS_FRAGMENT)
                        .commit()

            }
        }

        buttonAbout.setOnClickListener {
            val fragment = supportFragmentManager
                    .findFragmentByTag(AboutFragment.TAG_YAFINA_ABOUT_FRAGMENT)

            if (fragment == null) {
                val aboutFragment = AboutFragment.getInstance()

                supportFragmentManager.beginTransaction()
                .replace(R.id.balance_container, aboutFragment,
                        AboutFragment.TAG_YAFINA_ABOUT_FRAGMENT)
                .commit()

            }
        }
    }

    override fun switchNewTab(tab: MainTabs) {
        tabState = tab

    }

}
