package com.zavijavasoft.yafina.ui.settings

import com.arellomobile.mvp.MvpView

interface SettingsView : MvpView {
    fun update(currencyList: List<String>)
}