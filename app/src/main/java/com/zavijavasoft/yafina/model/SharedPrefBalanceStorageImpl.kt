package com.zavijavasoft.yafina.model

import android.content.Context
import android.preference.PreferenceManager
import com.zavijavasoft.yafina.YaFinaApplication
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject


class SharedPrefBalanceStorageImpl : BalanceStorage {

    companion object {
        const val SHARED_PREF_BALANCE_LASTUPDATED_KEY = "SHARED_PREF_BALANCE_LASTUPDATED_KEY"
        const val SHARED_PREF_BALANCE_CURRENCIES_KEY = "SHARED_PREF_BALANCE_CURRENCIES_KEY"
        const val SHARED_PREF_BALANCE_VALUES_KEY = "SHARED_PREF_BALANCE_VALUES_KEY"
    }


    @Inject
    lateinit var context: Context

    init {
        YaFinaApplication.component.inject(this)
    }

    override fun getBalance(): Single<BalanceEntity> {
        return Single.create<BalanceEntity> {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val lastUpdated = preferences.getLong(SHARED_PREF_BALANCE_LASTUPDATED_KEY, Date().time)
            val currenciesString = preferences.getString(SHARED_PREF_BALANCE_CURRENCIES_KEY, "")
            val valuesString = preferences.getString(SHARED_PREF_BALANCE_VALUES_KEY, "")

            val balance = BalanceEntity.getInstance(currenciesString, valuesString, Date(lastUpdated))
            it.onSuccess(balance)
        }
    }

    override fun setBalance(balance: BalanceEntity): Completable {
        return Completable.create {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SHARED_PREF_BALANCE_LASTUPDATED_KEY, balance.lastUpdated.time)
            editor.putString(SHARED_PREF_BALANCE_CURRENCIES_KEY, balance.toCurrenciesString())
            editor.putString(SHARED_PREF_BALANCE_VALUES_KEY, balance.toValuesString())
            editor.apply()
            it.onComplete()
        }
    }

}