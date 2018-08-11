package com.zavijavasoft.yafina.model

import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

interface TransactionStorage {
    fun add(transaction: TransactionInfo): Completable
    fun remove(transaction: TransactionInfo): Completable
    fun update(transaction: TransactionInfo): Completable
    fun findAll(): Single<List<TransactionInfo>>
    fun findAllBetween(dateFrom: Date, dateTo: Date): Single<List<TransactionInfo>>
}
