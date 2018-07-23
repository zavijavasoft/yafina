package com.zavijavasoft.yafina.ui

import com.arellomobile.mvp.MvpView

interface ISettingsView : MvpView {
    fun update(currencyList: List<String>)
}