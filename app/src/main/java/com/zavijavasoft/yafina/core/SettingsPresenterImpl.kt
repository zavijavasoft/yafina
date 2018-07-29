package com.zavijavasoft.yafina.core

import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.CurrencyStorage
import com.zavijavasoft.yafina.ui.settings.SettingsView
import javax.inject.Inject

class SettingsPresenterImpl @Inject constructor(private val storage: CurrencyStorage) : MvpPresenter<SettingsView>(), SettingsPresenter {

    override fun addCurrency(currency: String) {
        val list = storage.getCurrencyList()
        if (currency !in list) {
            storage.addCurrency(currency)
        }
        viewState.update(storage.getCurrencyList())
    }

    override fun removeCurrency(currency: String) {
        val list = storage.getCurrencyList()
        if (currency in list) {
            storage.removeCurrency(currency)
        }
        viewState.update(storage.getCurrencyList())
    }

}