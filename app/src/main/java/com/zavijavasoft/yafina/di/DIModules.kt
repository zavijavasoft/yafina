package com.zavijavasoft.yafina.di

import android.support.annotation.NonNull
import com.zavijavasoft.yafina.model.FinanceTrackerImpl
import com.zavijavasoft.yafina.model.FinanceTracker
import com.zavijavasoft.yafina.model.TransactionStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class TrackerModule {

    @Singleton
    @Provides
    @NonNull
    fun getTracker(transactionStorage: TransactionStorage): FinanceTracker {
        return FinanceTrackerImpl(transactionStorage)
    }

}


