package com.zavijavasoft.yafina.di

import android.support.annotation.NonNull
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.data.room.AppDatabase
import com.zavijavasoft.yafina.data.room.dao.AccountDao
import com.zavijavasoft.yafina.data.room.dao.ArticleDao
import com.zavijavasoft.yafina.data.room.dao.CurrencyDao
import com.zavijavasoft.yafina.data.room.dao.TransactionDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    @NonNull
    fun getAppDatabase(app: YaFinaApplication): AppDatabase {
        return AppDatabase.getInstance(app)
    }

    @Singleton
    @Provides
    @NonNull
    fun getAccountDao(db: AppDatabase): AccountDao {
        return db.getAccountDao()
    }

    @Singleton
    @Provides
    @NonNull
    fun getArticleDao(db: AppDatabase): ArticleDao {
        return db.getArticleDao()
    }

    @Singleton
    @Provides
    @NonNull
    fun getTransactionDao(db: AppDatabase): TransactionDao {
        return db.getTransactionDao()
    }

    @Singleton
    @Provides
    @NonNull
    fun getCurrencyDao(db: AppDatabase): CurrencyDao {
        return db.getCurrencyDao()
    }
}