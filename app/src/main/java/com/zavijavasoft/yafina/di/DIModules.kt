package com.zavijavasoft.yafina.di

import android.support.annotation.NonNull
import com.zavijavasoft.yafina.data.AccountsStorageImpl
import com.zavijavasoft.yafina.data.ArticlesStorageImpl
import com.zavijavasoft.yafina.data.CurrencyStorageImpl
import com.zavijavasoft.yafina.data.TransactionStorageImpl
import com.zavijavasoft.yafina.data.room.dao.AccountDao
import com.zavijavasoft.yafina.data.room.dao.ArticleDao
import com.zavijavasoft.yafina.data.room.dao.CurrencyDao
import com.zavijavasoft.yafina.data.room.dao.TransactionDao
import com.zavijavasoft.yafina.model.*
import com.zavijavasoft.yafina.services.CbrCurrencyMonitorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


typealias ConcreteFinanceTrackerImpl = FinanceTrackerImpl
typealias ConcreteBalanceStorageImpl = SharedPrefBalanceStorageImpl


typealias ConcreteTransactionStorageImpl = TransactionStorageImpl
typealias ConcreteCurrencyMonitorImpl = CbrCurrencyMonitorImpl
typealias ConcreteCurrencyStorageImpl = CurrencyStorageImpl
typealias ConcreteArticleStorageImpl = ArticlesStorageImpl
typealias ConcreteAccountsStorageImpl = AccountsStorageImpl

@Module(includes = [(AndroidSupportInjectionModule::class), (RoomModule::class),
    (AccountsStorageModule::class), (ArticlesStorageModule::class),
    (CurrencyStorageModule::class), (TransactionsStorageModule::class)])
interface AppModule {

    @Singleton
    @Binds
    @NonNull
    fun newTracker(tracker: ConcreteFinanceTrackerImpl): FinanceTracker

    @Singleton
    @Binds
    @NonNull
    fun newCurrencyMonitor(currencyMonitor: ConcreteCurrencyMonitorImpl): CurrencyMonitor

    @Singleton
    @Binds
    @NonNull
    fun getBalanceStorage(balanceStorage: ConcreteBalanceStorageImpl): BalanceStorage

}


@Module
class CurrencyStorageModule {

    @Singleton
    @Provides
    @NonNull
    fun getCurrencyStorage(dao: CurrencyDao): CurrencyStorage {
        return ConcreteCurrencyStorageImpl(dao)
    }

}

@Module
class TransactionsStorageModule {

    @Singleton
    @Provides
    @NonNull
    fun getTransactionStorage(dao: TransactionDao): TransactionStorage {
        return ConcreteTransactionStorageImpl(dao)
    }

}


@Module
class ArticlesStorageModule {
    @Singleton
    @Provides
    @NonNull
    fun getArticlesStorage(dao: ArticleDao): ArticlesStorage {
        return ConcreteArticleStorageImpl(dao)
    }
}

@Module
class AccountsStorageModule {
    @Singleton
    @Provides
    @NonNull
    fun getAccountStorage(dao: AccountDao): AccountsStorage {
        return ConcreteAccountsStorageImpl(dao)
    }
}