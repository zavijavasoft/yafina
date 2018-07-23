package com.zavijavasoft.yafina.di

import com.zavijavasoft.yafina.core.BalancePresenterImpl
import com.zavijavasoft.yafina.core.SettingsPresenterImpl
import com.zavijavasoft.yafina.stub.StubCurrencyMonitorModule
import com.zavijavasoft.yafina.stub.StubCurrencyStorageModule
import com.zavijavasoft.yafina.stub.StubTransactionsStorageModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(TrackerModule::class,
        StubCurrencyStorageModule::class,
        StubTransactionsStorageModule::class,
        StubCurrencyMonitorModule::class))
@Singleton
interface AppComponent {
    fun inject(balancePresenter: BalancePresenterImpl)
    fun inject(settingsPresenter: SettingsPresenterImpl)
}
