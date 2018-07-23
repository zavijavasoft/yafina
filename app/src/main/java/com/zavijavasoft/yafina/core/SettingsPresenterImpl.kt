package com.zavijavasoft.yafina.core

import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.model.CurrencyStorage
import com.zavijavasoft.yafina.ui.SettingsView
import javax.inject.Inject

class SettingsPresenterImpl : MvpPresenter<SettingsView>(), SettingsPresenter {

    @Inject
    lateinit var storage: CurrencyStorage

    init {
        YaFinaApplication.component.inject(this)
    }


    override fun addCurrency(currency: String) {
        val list = storage.getCurrencyList()
        if (currency !in list)
            storage.addCurrency(currency)
        viewState.update(storage.getCurrencyList())
    }

    override fun removeCurrency(currency: String) {
        val list = storage.getCurrencyList()
        if (currency in list)
            storage.removeCurrency(currency)
        viewState.update(storage.getCurrencyList())
    }

}