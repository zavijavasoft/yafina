package com.zavijavasoft.yafina.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.zavijavasoft.yafina.data.room.dao.AccountDao
import com.zavijavasoft.yafina.data.room.dao.ArticleDao
import com.zavijavasoft.yafina.data.room.dao.CurrencyDao
import com.zavijavasoft.yafina.data.room.dao.TransactionDao
import com.zavijavasoft.yafina.model.AccountEntity
import com.zavijavasoft.yafina.model.ArticleEntity

@Database(entities = [AccountEntity::class, ArticleEntity::class,
    CurrencyEntity::class, TransactionEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getAccountDao(): AccountDao
    abstract fun getArticleDao(): ArticleDao
    abstract fun getTransactionDao(): TransactionDao
    abstract fun getCurrencyDao(): CurrencyDao

}