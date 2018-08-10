package com.zavijavasoft.yafina.data

import com.zavijavasoft.yafina.data.room.dao.AccountDao
import com.zavijavasoft.yafina.model.AccountEntity
import com.zavijavasoft.yafina.model.AccountsStorage
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AccountsStorageImpl
@Inject constructor(private val dao: AccountDao)
    : AccountsStorage {

    override fun deleteAccount(account: AccountEntity): Completable {
        return Completable.fromAction {
            dao.deleteAccount(account)
        }
    }

    override fun updateAccount(account: AccountEntity): Completable {
        return Completable.fromAction {
            dao.updateAccount(account)
        }
    }

    override fun getAccounts(): Single<List<AccountEntity>> {
        return Single.fromCallable {
            dao.getAccounts().sortedBy { it.id }
        }
    }

    override fun getAccountById(id: Long): Single<AccountEntity> {
        return Single.fromCallable {
            dao.getAccountById(id)
        }
    }

    override fun addAccount(account: AccountEntity): Completable {
        return Completable.fromAction {
            dao.insertAccount(account)
        }
    }

}