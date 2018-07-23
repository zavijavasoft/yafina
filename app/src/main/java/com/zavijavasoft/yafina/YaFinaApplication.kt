package com.zavijavasoft.yafina

import com.zavijavasoft.yafina.di.AppComponent
import com.zavijavasoft.yafina.di.DaggerAppComponent
import com.zavijavasoft.yafina.di.TrackerModule
import com.zavijavasoft.yafina.stub.StubCurrencyMonitorModule
import com.zavijavasoft.yafina.stub.StubCurrencyStorageModule
import com.zavijavasoft.yafina.stub.StubTransactionsStorageModule

class YaFinaApplication : android.app.Application() {


    companion object {
        @JvmStatic
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = buildComponent()
    }

    fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .trackerModule(TrackerModule())
                .stubCurrencyMonitorModule(StubCurrencyMonitorModule())
                .stubTransactionsStorageModule(StubTransactionsStorageModule())
                .stubCurrencyStorageModule(StubCurrencyStorageModule())
                .build()
    }

}