package com.zavijavasoft.yafina


import android.os.Bundle
import android.widget.ImageButton
import butterknife.BindView
import butterknife.ButterKnife
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


    var tabState: MainTabs = MainTabs.BALANCE




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

        ButterKnife.bind(this)

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
