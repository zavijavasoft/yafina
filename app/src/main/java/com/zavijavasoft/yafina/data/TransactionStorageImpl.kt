package com.zavijavasoft.yafina.data

import com.zavijavasoft.yafina.data.room.Converters
import com.zavijavasoft.yafina.data.room.TransactionEntity
import com.zavijavasoft.yafina.data.room.dao.TransactionDao
import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.model.TransactionStorage
import io.reactivex.Single
import javax.inject.Inject

class TransactionStorageImpl
@Inject constructor(private val dao: TransactionDao)
    : TransactionStorage {

    override fun add(transaction: TransactionInfo): Single<List<TransactionInfo>> {
        return Single.fromCallable {
            val transactionEntity = Converters.toTransactionEntity(transaction)
            dao.insertTransaction(transactionEntity)
            dao.getTransactions().map { Converters.toTransactionInfo(transactionEntity)}
        }
    }

    override fun remove(transactionId: Long): Single<List<TransactionInfo>> {
        return Single.fromCallable {
            val transactionEntity = dao.getTransactionById(transactionId)
            dao.deleteTransaction(transactionEntity)
            dao.getTransactions().map { Converters.toTransactionInfo(it) }
        }
    }

    override fun update(transaction: TransactionInfo): Single<List<TransactionInfo>> {
        return Single.fromCallable {
            val transactionEntity = TransactionEntity(transaction.transactionId, transaction.sum,
                    transaction.article, transaction.accountId, transaction.comment)
            dao.updateTransaction(transactionEntity)
            dao.getTransactions().map { Converters.toTransactionInfo(it) }
        }
    }

    override fun findAll(): Single<List<TransactionInfo>> {
        return Single.fromCallable {
            dao.getTransactions().map { Converters.toTransactionInfo(it) }
        }
    }

}

