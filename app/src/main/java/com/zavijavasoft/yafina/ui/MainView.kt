package com.zavijavasoft.yafina.ui

import com.arellomobile.mvp.MvpView

enum class MainTabs {
    BALANCE,
    TRANSACTIONS_LIST,
    OPERATION,
    SETTINGS,
    ABOUT
}

interface MainView : MvpView {
    fun switchNewTab(tab: MainTabs)
}