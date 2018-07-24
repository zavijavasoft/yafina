package com.zavijavasoft.yafina.di

import android.support.annotation.NonNull
import com.zavijavasoft.yafina.model.*
import com.zavijavasoft.yafina.stub.StubCurrencyMonitorImpl
import com.zavijavasoft.yafina.stub.StubCurrencyStorageImpl
import com.zavijavasoft.yafina.stub.StubTransactionStorageImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


typealias ConcreteFinanceTrackerImpl = FinanceTrackerImpl
typealias ConcreteTransactionStorageImpl = StubTransactionStorageImpl
typealias ConcreteCurrencyMonitorImpl = StubCurrencyMonitorImpl
typealias ConcreteCurrencyStorageImpl = StubCurrencyStorageImpl

@Module(includes = [(AndroidSupportInjectionModule::class),
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

