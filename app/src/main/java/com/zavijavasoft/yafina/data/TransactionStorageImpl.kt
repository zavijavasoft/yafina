package com.zavijavasoft.yafina.data

import com.zavijavasoft.yafina.data.room.TransactionEntity
import com.zavijavasoft.yafina.data.room.dao.TransactionDao
import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.model.TransactionStorage
import java.util.*
import javax.inject.Inject

class TransactionStorageImpl
@Inject constructor(private val dao: TransactionDao)
    : TransactionStorage {

    override fun add(transaction: TransactionInfo): List<TransactionInfo> {
        val transactionEntity = TransactionEntity(transaction.transactionId, transaction.sum,
                transaction.article, transaction.accountId, transaction.comment)
        dao.insertTransaction(transactionEntity)
        return findAll()
    }

    override fun remove(transactionId: Long): List<TransactionInfo> {
        val transactionEntity = dao.getTransactionById(transactionId)
        dao.deleteTransaction(transactionEntity)
        return findAll()
    }

    override fun update(transaction: TransactionInfo): List<TransactionInfo> {
        val transactionEntity = TransactionEntity(transaction.transactionId, transaction.sum,
                transaction.article, transaction.accountId, transaction.comment)
        dao.updateTransaction(transactionEntity)
        return findAll()
    }

    override fun findAll(): List<TransactionInfo> {
        return dao.getTransactions().map { TransactionInfo(it.sum, it.article, Date(it.transactionId),
                it.accountId, it.comment) }
    }

}

