package com.zavijavasoft.yafina.model

import io.reactivex.Single

interface TransactionStorage {
    fun add(transaction: TransactionInfo): Single<List<TransactionInfo>>
    fun remove(transactionId: Long): Single<List<TransactionInfo>>
    fun update(transaction: TransactionInfo): Single<List<TransactionInfo>>
    fun findAll(): Single<List<TransactionInfo>>
}
