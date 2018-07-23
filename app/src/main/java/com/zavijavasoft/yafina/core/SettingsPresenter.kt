package com.zavijavasoft.yafina.core

import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.model.ICurrencyStorage
import com.zavijavasoft.yafina.ui.ISettingsView
import javax.inject.Inject

class SettingsPresenter : MvpPresenter<ISettingsView>(), ISettingsPresenter {

    @Inject
    lateinit var storage: ICurrencyStorage

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