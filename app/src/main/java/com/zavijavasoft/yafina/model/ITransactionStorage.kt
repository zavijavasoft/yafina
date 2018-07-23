package com.zavijavasoft.yafina.model

interface ITransactionStorage {
    fun add(transaction: TransactionInfo): List<TransactionInfo>
    fun remove(transactionId: Long): List<TransactionInfo>
    fun update(transaction: TransactionInfo): List<TransactionInfo>
    fun findAll(): List<TransactionInfo>
}
