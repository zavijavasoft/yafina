package com.zavijavasoft.yafina.data.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.zavijavasoft.yafina.data.room.dao.*
import com.zavijavasoft.yafina.model.*
import java.util.concurrent.Executors

@Database(entities = [AccountEntity::class, ArticleEntity::class, CurrencyEntity::class,
    OneTimeTransactionEntity::class, ScheduledTransactionEntity::class, ArticleTemplateEntity::class],
        version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    companion object {

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                instance ?: buildDatabase(context).also { instance = it }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "finance")
                    .addCallback(object: RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            Executors.newSingleThreadExecutor().execute {
                                val appDb = getInstance(context)
                                insertAccounts(appDb.getAccountDao())
                                insertArticles(appDb.getArticleDao())
                                insertCurrencies(appDb.getCurrencyDao())
                                insertArticleTemplates(appDb.getArticleTemplateDao())
                            }
                        }
                    })
                    .build()
        }

        private fun insertAccounts(dao: AccountDao) {
            dao.insertAccount(AccountEntity(1, "RUR",
                    "Наличные в рублях", "Все, что есть по карманам"))
        }

        private fun insertArticles(dao: ArticleDao) {
            dao.insertArticles(
                    ArticleEntity(1, ArticleType.OUTCOME,
                            "Продукты", "То, что едят"),
                    ArticleEntity(2, ArticleType.OUTCOME,
                            "Одежда", "То, что надевают"),
                    ArticleEntity(3, ArticleType.OUTCOME,
                            "Выплаты по кредиту", "Отдавать свои кровные"),
                    ArticleEntity(4, ArticleType.OUTCOME,
                            "ЖКХ", "За воду и свет"),
                    ArticleEntity(5, ArticleType.INCOME,
                            "Зарплата", "Зарплата -- она и есть зарплата"),
                    ArticleEntity(6, ArticleType.INCOME,
                            "Дивиденды", "Доход от акций и облигаций"),
                    ArticleEntity(7, ArticleType.OUTCOME,
                            "Транспорт", "На проезд"),
                    ArticleEntity(8, ArticleType.OUTCOME,
                            "Прочие расходы", "Не помню куда"),
                    ArticleEntity(9, ArticleType.INCOME,
                            "Прочие доходы", "Не помню откуда")
            )
        }

        private fun insertArticleTemplates(dao: ArticleTemplateDao) {
            dao.insertTemplates(
                    ArticleTemplateEntity(1, 1, false,
                            "То, что едят"),
                    ArticleTemplateEntity(2, 2, false,
                            "То, что надевают"),
                    ArticleTemplateEntity(3, 3, true,
                            "Отдавать свои кровные", TransactionScheduleTimeUnit.MONTH),
                    ArticleTemplateEntity(4, 4, true,
                            "За воду и свет", TransactionScheduleTimeUnit.MONTH),
                    ArticleTemplateEntity(5, 5, true,
                            "Зарплата -- она и есть зарплата", TransactionScheduleTimeUnit.MONTH),
                    ArticleTemplateEntity(6, 6, true,
                            "Доход от акций и облигаций", TransactionScheduleTimeUnit.MONTH),
                    ArticleTemplateEntity(7, 7, false,
                            "На проезд"),
                    ArticleTemplateEntity(8, 8, false,
                            "Не помню куда"),
                    ArticleTemplateEntity(9, 9, false,
                            "Не помню откуда")
            )
        }

        private fun insertCurrencies(dao: CurrencyDao) {
            dao.insertCurrencies(
                    CurrencyEntity("USD"),
                    CurrencyEntity("RUR"),
                    CurrencyEntity("KZT"),
                    CurrencyEntity("EUR")
            )
        }
    }

    abstract fun getAccountDao(): AccountDao
    abstract fun getArticleDao(): ArticleDao
    abstract fun getOneTimeTransactionDao(): OneTimeTransactionDao
    abstract fun getScheduledTransactionDao(): ScheduledTransactionDao
    abstract fun getCurrencyDao(): CurrencyDao
    abstract fun getArticleTemplateDao(): ArticleTemplateDao

}