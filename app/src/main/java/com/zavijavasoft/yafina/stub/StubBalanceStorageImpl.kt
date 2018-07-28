package com.zavijavasoft.yafina.stub

import com.zavijavasoft.yafina.model.BalanceEntity
import com.zavijavasoft.yafina.model.BalanceStorage
import rx.Completable
import rx.Single
import java.util.*

class StubBalanceStorageImpl : BalanceStorage {
    override fun getBalance(): Single<BalanceEntity> {
        val map = mutableMapOf<String, Float>()
        map["RUR"] = 666.51f
        map["USD"] = 42.0f
        val balance = BalanceEntity(map.toMap(), Date())
        return Single.just(balance)
    }

    override fun setBalance(balance: BalanceEntity): Completable {
        return Completable.complete()
    }
}