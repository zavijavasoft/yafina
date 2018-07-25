package com.zavijavasoft.yafina.core

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.ui.MainView
import com.zavijavasoft.yafina.ui.MainTabs


@InjectViewState
class MainPresenterImpl : MvpPresenter<MainView>(), MainPresenter {
    override fun showBalanceView() {
        viewState.switchNewTab(MainTabs.BALANCE)
    }

    override fun showTransactionsListView() {
        viewState.switchNewTab(MainTabs.TRANSACTIONS_LIST)
    }

    override fun showSettingsView() {
        viewState.switchNewTab(MainTabs.SETTINGS)
    }

    override fun showAboutView() {
        viewState.switchNewTab(MainTabs.ABOUT)
    }
}