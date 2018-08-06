package com.zavijavasoft.yafina.data

import com.zavijavasoft.yafina.data.room.Converters
import com.zavijavasoft.yafina.data.room.dao.OneTimeTransactionDao
import com.zavijavasoft.yafina.data.room.dao.ScheduledTransactionDao
import com.zavijavasoft.yafina.model.OneTimeTransactionInfo
import com.zavijavasoft.yafina.model.ScheduledTransactionInfo
import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.model.TransactionStorage
import com.zavijavasoft.yafina.services.scheduleTransaction
import io.reactivex.Single
import javax.inject.Inject

class TransactionStorageImpl
@Inject constructor(private val oneTimeDao: OneTimeTransactionDao,
                    private val scheduledDao: ScheduledTransactionDao)
    : TransactionStorage {

    override fun add(transaction: TransactionInfo): Single<List<TransactionInfo>> {
        return Single.fromCallable {
            when (transaction) {
                is OneTimeTransactionInfo -> {
                    val transactionEntity = Converters.toOneTimeTransactionEntity(transaction)
                    oneTimeDao.insertTransaction(transactionEntity)
                }
                is ScheduledTransactionInfo -> {
                    val transactionEntity = Converters.toScheduledTransactionEntity(transaction)
                    scheduledDao.insertTransaction(transactionEntity)
                    scheduleTransaction(transactionEntity)
                }
            }
            val oneTimeTransactions = oneTimeDao.getTransactions()
                    .map { it -> Converters.toOneTimeTransactionInfo(it)}
            val scheduledTransactions = scheduledDao.getTransactions()
                    .map { it -> Converters.toScheduledTransactionInfo(it)}
            (oneTimeTransactions + scheduledTransactions).sortedBy { it.transactionId }
        }
    }

    override fun remove(transaction: TransactionInfo): Single<List<TransactionInfo>> {
        return Single.fromCallable {
            when (transaction) {
                is OneTimeTransactionInfo -> {
                    val transactionEntity = Converters.toOneTimeTransactionEntity(transaction)
                    oneTimeDao.deleteTransaction(transactionEntity)
                }
                is ScheduledTransactionInfo -> {
                    val transactionEntity = Converters.toScheduledTransactionEntity(transaction)
                    scheduledDao.deleteTransaction(transactionEntity)
                }
            }
            val oneTimeTransactions = oneTimeDao.getTransactions()
                    .map { it -> Converters.toOneTimeTransactionInfo(it)}
            val scheduledTransactions = scheduledDao.getTransactions()
                    .map { it -> Converters.toScheduledTransactionInfo(it)}
            (oneTimeTransactions + scheduledTransactions).sortedBy { it.transactionId }
        }
    }

    override fun update(transaction: TransactionInfo): Single<List<TransactionInfo>> {
        return Single.fromCallable {
            when (transaction) {
                is OneTimeTransactionInfo -> {
                    val transactionEntity = Converters.toOneTimeTransactionEntity(transaction)
                    oneTimeDao.updateTransaction(transactionEntity)
                }
                is ScheduledTransactionInfo -> {
                    val transactionEntity = Converters.toScheduledTransactionEntity(transaction)
                    scheduledDao.updateTransaction(transactionEntity)
                }
            }
            val oneTimeTransactions = oneTimeDao.getTransactions()
                    .map { it -> Converters.toOneTimeTransactionInfo(it)}
            val scheduledTransactions = scheduledDao.getTransactions()
                    .map { it -> Converters.toScheduledTransactionInfo(it)}
            (oneTimeTransactions + scheduledTransactions).sortedBy { it.transactionId }
        }
    }

    override fun findAll(): Single<List<TransactionInfo>> {
        return Single.fromCallable {
            val oneTimeTransactions = oneTimeDao.getTransactions()
                    .map { it -> Converters.toOneTimeTransactionInfo(it)}
            val scheduledTransactions = scheduledDao.getTransactions()
                    .map { it -> Converters.toScheduledTransactionInfo(it)}
            (oneTimeTransactions + scheduledTransactions).sortedBy { it.transactionId }
        }
    }

}

