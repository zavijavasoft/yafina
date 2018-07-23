package com.zavijavasoft.yafina


import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.widget.ImageButton
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.zavijavasoft.yafina.core.MainPresenter
import com.zavijavasoft.yafina.ui.*

class YaFinaActivity : MvpAppCompatActivity(), IMainView {


    @BindView(R.id.image_button_balance)
    lateinit var buttonBalance: ImageButton

    // @BindView(R.id.image_button_transactions)
    lateinit var buttonTransacionsList: ImageButton

    @BindView(R.id.image_button_settings)
    lateinit var buttonSettings: ImageButton

    @BindView(R.id.image_button_about)
    lateinit var buttonAbout: ImageButton

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter


    lateinit var _fragmentManager: FragmentManager
    var tabState: MainTabs = MainTabs.BALANCE

    val balanceFragment = BalanceFragment.getInstance()
    val aboutFragment = AboutFragment.getInstance()
    val settingsFragment = SettingsFragment.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

        ButterKnife.bind(this)

        _fragmentManager = supportFragmentManager

        if (null == savedInstanceState &&
                null == _fragmentManager.findFragmentByTag(BalanceFragment.TAG_YAFINA_BALANCE_FRAGMENT)) {
            val fragmentTransaction = _fragmentManager
                    .beginTransaction()
            // добавляем фрагмент
            fragmentTransaction.add(R.id.balance_container,
                    balanceFragment, BalanceFragment.TAG_YAFINA_BALANCE_FRAGMENT)
            fragmentTransaction.commit()
        }


        initNavigationButtons()
    }


    private fun initNavigationButtons() {

        buttonBalance.setOnClickListener {
            val fragment = _fragmentManager
                    .findFragmentByTag(BalanceFragment.TAG_YAFINA_BALANCE_FRAGMENT)

            if (fragment == null) {

                val fragmentTransaction = _fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.balance_container, balanceFragment,
                        BalanceFragment.TAG_YAFINA_BALANCE_FRAGMENT)
                fragmentTransaction.commit()

            }
        }

        buttonSettings.setOnClickListener {
            val fragment = _fragmentManager
                    .findFragmentByTag(SettingsFragment.TAG_YAFINA_SETTINGS_FRAGMENT)

            if (fragment == null) {

                val fragmentTransaction = _fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.balance_container, settingsFragment,
                        SettingsFragment.TAG_YAFINA_SETTINGS_FRAGMENT)
                fragmentTransaction.commit()

            }
        }

        buttonAbout.setOnClickListener {
            val fragment = _fragmentManager
                    .findFragmentByTag(AboutFragment.TAG_YAFINA_ABOUT_FRAGMENT)

            if (fragment == null) {

                val fragmentTransaction = _fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.balance_container, aboutFragment,
                        AboutFragment.TAG_YAFINA_ABOUT_FRAGMENT)
                fragmentTransaction.commit()

            }
        }
    }

    override fun switchNewTab(tab: MainTabs) {
        tabState = tab

    }

}
