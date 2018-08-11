package com.zavijavasoft.yafina.ui.settings

import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.CurrencyStorage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SettingsPresenterImpl @Inject constructor(private val storage: CurrencyStorage) : MvpPresenter<SettingsView>(), SettingsPresenter {

    private fun updateViewState() {
        storage.getCurrencyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    viewState.update(it)
                }
    }

}