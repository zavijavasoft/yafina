package com.zavijavasoft.yafina.ui

import com.arellomobile.mvp.MvpView

enum class MainTabs {
    BALANCE,
    TRANSACTIONS_LIST,
    SETTINGS,
    ABOUT
}

interface IMainView : MvpView {
    fun switchNewTab(tab: MainTabs)
}