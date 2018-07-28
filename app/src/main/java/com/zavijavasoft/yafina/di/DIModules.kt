package com.zavijavasoft.yafina.di

import android.support.annotation.NonNull
import com.zavijavasoft.yafina.model.*
import com.zavijavasoft.yafina.stub.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


typealias ConcreteFinanceTrackerImpl = FinanceTrackerImpl
typealias ConcreteBalanceStorageImpl = SharedPrefBalanceStorageImpl


typealias ConcreteTransactionStorageImpl = StubTransactionStorageImpl
typealias ConcreteCurrencyMonitorImpl = StubCurrencyMonitorImpl
typealias ConcreteCurrencyStorageImpl = StubCurrencyStorageImpl
typealias ConcreteArticleStorageImpl = StubArticlesStorageImpl
typealias ConcreteAccountsStorageImpl = StubAccountsStorageImpl

@Module(includes = [(AndroidSupportInjectionModule::class),
    (CurrencyStorageModule::class), (TransactionsStorageModule::class), (BalanceStorageModule::class),
    (ArticlesStorageModule::class), (AccountsStorageModule::class)])
interface AppModule {

    @Singleton
    @Binds
    @NonNull
    fun newTracker(tracker: ConcreteFinanceTrackerImpl): FinanceTracker

    @Singleton
    @Binds
    @NonNull
    fun newCurrencyMonitor(currencyMonitor: ConcreteCurrencyMonitorImpl): CurrencyMonitor


}


@Module
class CurrencyStorageModule {

    @Singleton
    @Provides
    @NonNull
    fun getCurrencyStorage(): CurrencyStorage {
        return ConcreteCurrencyStorageImpl()
    }

}

@Module
class TransactionsStorageModule {

    @Singleton
    @Provides
    @NonNull
    fun getTransactionStorage(): TransactionStorage {
        return ConcreteTransactionStorageImpl()
    }

}

@Module
class BalanceStorageModule {
    @Singleton
    @Provides
    @NonNull
    fun getBalanceStorage(): BalanceStorage {
        return ConcreteBalanceStorageImpl()
    }
}

@Module
class ArticlesStorageModule {
    @Singleton
    @Provides
    @NonNull
    fun getArticlesStorage(): ArticlesStorage {
        return ConcreteArticleStorageImpl()
    }
}

@Module
class AccountsStorageModule {
    @Singleton
    @Provides
    @NonNull
    fun getAccountStorage(): AccountsStorage {
        return ConcreteAccountsStorageImpl()
    }
}