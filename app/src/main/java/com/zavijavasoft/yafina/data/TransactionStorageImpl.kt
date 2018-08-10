package com.zavijavasoft.yafina.data

import com.zavijavasoft.yafina.data.room.Converters
import com.zavijavasoft.yafina.data.room.dao.OneTimeTransactionDao
import com.zavijavasoft.yafina.data.room.dao.ScheduledTransactionDao
import com.zavijavasoft.yafina.model.OneTimeTransactionInfo
import com.zavijavasoft.yafina.model.ScheduledTransactionInfo
import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.model.TransactionStorage
import com.zavijavasoft.yafina.services.scheduleTransaction
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class TransactionStorageImpl
@Inject constructor(private val oneTimeDao: OneTimeTransactionDao,
                    private val scheduledDao: ScheduledTransactionDao)
    : TransactionStorage {

    override fun add(transaction: TransactionInfo): Completable {
        return Completable.fromAction {
            when (transaction) {
                is ScheduledTransactionInfo -> {
                    val transactionEntity = Converters.toScheduledTransactionEntity(transaction)
                    scheduledDao.insertTransaction(transactionEntity)
                    scheduleTransaction(transactionEntity)
                }
                is OneTimeTransactionInfo -> {
                    val transactionEntity = Converters.toOneTimeTransactionEntity(transaction)
                    oneTimeDao.insertTransaction(transactionEntity)
                }
            }
        }
    }

    override fun remove(transaction: TransactionInfo): Completable {
        return Completable.fromAction {
            when (transaction) {
                is ScheduledTransactionInfo -> {
                    val transactionEntity = Converters.toScheduledTransactionEntity(transaction)
                    scheduledDao.deleteTransaction(transactionEntity)
                }
                is OneTimeTransactionInfo -> {
                    val transactionEntity = Converters.toOneTimeTransactionEntity(transaction)
                    oneTimeDao.deleteTransaction(transactionEntity)
                }
            }
        }
    }

    override fun update(transaction: TransactionInfo): Completable {
        return Completable.fromAction {
            when (transaction) {
                is ScheduledTransactionInfo -> {
                    val transactionEntity = Converters.toScheduledTransactionEntity(transaction)
                    scheduledDao.updateTransaction(transactionEntity)
                }
                is OneTimeTransactionInfo -> {
                    val transactionEntity = Converters.toOneTimeTransactionEntity(transaction)
                    oneTimeDao.updateTransaction(transactionEntity)
                }
            }
        }
    }

    override fun findAll(): Single<List<TransactionInfo>> {
        return Single.fromCallable {
            val oneTimeTransactions = oneTimeDao.getTransactions()
                    .map { it -> Converters.toTransactionInfo(it)}
            val scheduledTransactions = scheduledDao.getTransactions()
                    .map { it -> Converters.toScheduledTransactionInfo(it)}
            (oneTimeTransactions + scheduledTransactions).sortedBy { it.transactionId }
        }
    }

    override fun findAllBetween(dateFrom: Date, dateTo: Date): Single<List<TransactionInfo>> {
        return Single.fromCallable {
            val oneTimeTransactions = oneTimeDao
                    .getTransactionsBetweenDate(dateFrom.time, dateTo.time)
                    .map { it -> Converters.toTransactionInfo(it) }
            val scheduledTransactions = scheduledDao
                    .getTransactionsBetweenDate(dateFrom.time, dateTo.time)
                    .map { it -> Converters.toScheduledTransactionInfo(it) }
            (oneTimeTransactions + scheduledTransactions)
                    .sortedBy { it.transactionId }
        }
    }
}

