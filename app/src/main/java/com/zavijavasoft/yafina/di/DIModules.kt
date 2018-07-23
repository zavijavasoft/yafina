package com.zavijavasoft.yafina.di

import android.support.annotation.NonNull
import com.zavijavasoft.yafina.model.FinanceTrackerImpl
import com.zavijavasoft.yafina.model.IFinanceTracker
import com.zavijavasoft.yafina.model.ITransactionStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class TrackerModule {

    @Singleton
    @Provides
    @NonNull
    fun getTracker(transactionStorage: ITransactionStorage): IFinanceTracker {
        return FinanceTrackerImpl(transactionStorage)
    }

}


