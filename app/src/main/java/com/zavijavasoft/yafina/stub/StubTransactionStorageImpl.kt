package com.zavijavasoft.yafina.stub

import com.zavijavasoft.yafina.model.OneTimeTransactionInfo
import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.model.TransactionStorage
import com.zavijavasoft.yafina.utils.TransactionType
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

class StubTransactionStorageImpl : TransactionStorage {

    val transactions: List<TransactionInfo> = listOf(
            OneTimeTransactionInfo(TransactionType.INCOME, 10000f, Date(100000), 1, articleId = 5),
            OneTimeTransactionInfo(TransactionType.OUTCOME, 10000f, Date(100041), 0, articleId = 3, accountIdFrom = 4),
            OneTimeTransactionInfo(TransactionType.INCOME, 10000f, Date(100031), 5, articleId = 4)
    )

    override fun add(transaction: TransactionInfo): Completable {
        return Completable.complete()
    }

    override fun remove(transaction: TransactionInfo): Completable {
        return Completable.complete()
    }

    override fun update(transaction: TransactionInfo): Completable {
        return Completable.complete()
    }

    override fun findAll(): Single<List<TransactionInfo>> {
        return Single.just(transactions)
    }

}

