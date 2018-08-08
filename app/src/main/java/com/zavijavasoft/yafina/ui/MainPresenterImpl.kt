package com.zavijavasoft.yafina.ui

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter


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

    override fun showOperationView() {
        viewState.switchNewTab(MainTabs.OPERATION)
    }
}