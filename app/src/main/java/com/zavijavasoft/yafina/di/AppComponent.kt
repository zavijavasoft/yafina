package com.zavijavasoft.yafina.di

import com.zavijavasoft.yafina.YaFinaApplication
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

        fun build(): AppComponent
    }


    fun inject(app: YaFinaApplication)
    fun inject(fragment: BalanceFragment)
    fun inject(fragment: SettingsFragment)
}
