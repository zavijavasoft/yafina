package com.zavijavasoft.yafina.services

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import com.zavijavasoft.yafina.data.room.AppDatabase
import com.zavijavasoft.yafina.data.room.ScheduledTransactionEntity
import com.zavijavasoft.yafina.model.TransactionScheduleTimeUnit
import java.util.*
import java.util.concurrent.TimeUnit

private const val ARG_SUM = "ARG_SUM"
private const val ARG_ARTICLE = "ARG_ARTICLE"
private const val ARG_ACCOUNT = "ARG_ACCOUNT"
private const val ARG_TIME_UNIT = "ARG_TIME_UNIT"

class ScheduledTransactionWorker : Worker() {

    override fun doWork(): Result {
        val transaction = parseData()
        insertTransaction(transaction)
        scheduleTransaction(transaction)
        return Result.SUCCESS
    }

    private fun parseData(): ScheduledTransactionEntity {
        val transactionId = Date().time
        val sum = inputData.getFloat(ARG_SUM, -1f)
        val articleId = inputData.getLong(ARG_ARTICLE, -1)
        val accountId = inputData.getLong(ARG_ACCOUNT, -1)
        val timeUnit = TransactionScheduleTimeUnit.values()[inputData.getInt(ARG_TIME_UNIT, -1)]
        return ScheduledTransactionEntity(transactionId, sum, articleId, accountId, "", timeUnit)
    }

    private fun insertTransaction(transaction: ScheduledTransactionEntity) {
        val db = AppDatabase.getInstance(applicationContext)
        db.getScheduledTransactionDao().insertTransaction(transaction)
    }

    companion object {

        fun getData(transactionEntity: ScheduledTransactionEntity): Data {
            return Data.Builder()
                    .putFloat(ARG_SUM, transactionEntity.sum)
                    .putLong(ARG_ARTICLE, transactionEntity.article)
                    .putLong(ARG_ACCOUNT, transactionEntity.accountId)
                    .putInt(ARG_TIME_UNIT, transactionEntity.period.ordinal)
                    .build()
        }

        fun getDelay(transaction: ScheduledTransactionEntity): Long {
            val calendar = Calendar.getInstance()
            when (transaction.period) {
                TransactionScheduleTimeUnit.WEEK -> calendar.add(Calendar.DAY_OF_YEAR, 7)
                TransactionScheduleTimeUnit.MONTH -> calendar.add(Calendar.MONTH, 1)
                TransactionScheduleTimeUnit.YEAR -> calendar.add(Calendar.YEAR, 1)
            }
            val transactionTime = transaction.transactionId
            return calendar.timeInMillis - transactionTime
        }
    }
}

fun scheduleTransaction(transaction: ScheduledTransactionEntity) {

    val workRequest = OneTimeWorkRequest.Builder(ScheduledTransactionWorker::class.java)
            .setInitialDelay(ScheduledTransactionWorker.getDelay(transaction), TimeUnit.MILLISECONDS)
            .setInputData(ScheduledTransactionWorker.getData(transaction))
            .build()
    WorkManager.getInstance().enqueue(workRequest)
}