package com.zavijavasoft.yafina.ui

import com.arellomobile.mvp.MvpView

interface SettingsView : MvpView {
    fun update(currencyList: List<String>)
}