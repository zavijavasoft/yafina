package com.zavijavasoft.yafina.di

import android.content.Context
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.model.SharedPrefBalanceStorageImpl
import com.zavijavasoft.yafina.ui.BalanceFragment
import com.zavijavasoft.yafina.ui.SettingsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Component(modules = [(AppModule::class)])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: YaFinaApplication): Builder

        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }


    fun inject(app: YaFinaApplication)
    fun inject(fragment: BalanceFragment)
    fun inject(fragment: SettingsFragment)

    fun inject(sharedPrefBalanceStorageImpl: SharedPrefBalanceStorageImpl)
}
