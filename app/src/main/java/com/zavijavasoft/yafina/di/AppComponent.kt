package com.zavijavasoft.yafina.di

import android.content.Context
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.ui.balance.BalanceFragment
import com.zavijavasoft.yafina.ui.operation.OperationFragment
import com.zavijavasoft.yafina.ui.settings.SettingsFragment
import com.zavijavasoft.yafina.ui.transactions.TransactionsFragment
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
    fun inject(fragment: OperationFragment)
    fun inject(fragment: TransactionsFragment)

}
