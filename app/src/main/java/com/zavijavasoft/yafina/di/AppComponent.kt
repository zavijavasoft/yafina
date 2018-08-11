package com.zavijavasoft.yafina.di

import android.content.Context
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.ui.balance.BalanceFragment
import com.zavijavasoft.yafina.ui.edittransaction.EditTransactionActivity
import com.zavijavasoft.yafina.ui.operation.OperationFragment
import com.zavijavasoft.yafina.ui.settings.SettingsFragment
import com.zavijavasoft.yafina.ui.settings.account.edit.EditAccountFragment
import com.zavijavasoft.yafina.ui.settings.account.list.AccountListFragment
import com.zavijavasoft.yafina.ui.settings.article.edit.EditArticleFragment
import com.zavijavasoft.yafina.ui.settings.article.list.ArticleListFragment
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
    fun inject(fragment: AccountListFragment)
    fun inject(fragment: EditAccountFragment)
    fun inject(fragment: ArticleListFragment)
    fun inject(fragment: EditArticleFragment)
    fun inject(activity: EditTransactionActivity)
}
