package com.zavijavasoft.yafina

import com.zavijavasoft.yafina.di.AppComponent
import com.zavijavasoft.yafina.di.DaggerAppComponent

class YaFinaApplication : android.app.Application() {

    companion object {
        @JvmStatic
        lateinit var component: AppComponent
    }


    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
                .builder()
                .application(this)
                .build()

    }


}