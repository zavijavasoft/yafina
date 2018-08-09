package com.zavijavasoft.yafina.model

import io.reactivex.Completable
import io.reactivex.Single

interface TransactionStorage {
    fun add(transaction: TransactionInfo): Completable
    fun remove(transaction: TransactionInfo): Completable
    fun update(transaction: TransactionInfo): Completable
    fun findAll(): Single<List<TransactionInfo>>
}
