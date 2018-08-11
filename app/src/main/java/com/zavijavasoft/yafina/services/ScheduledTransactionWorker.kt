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

private const val ARG_ID = "ARG_ID"

class ScheduledTransactionWorker : Worker() {

    override fun doWork(): Result {
        val transaction = parseData()
        insertTransaction(transaction)
        scheduleTransaction(transaction)
        return Result.SUCCESS
    }

    private fun parseData(): ScheduledTransactionEntity {
        val transactionId = inputData.getLong(ARG_ID, -1L)
        return getPreviousTransaction(transactionId)
    }

    private fun getPreviousTransaction(transactionId: Long): ScheduledTransactionEntity {
        val db = AppDatabase.getInstance(applicationContext)
        return db.getScheduledTransactionDao().getTransactionById(transactionId)
    }

    private fun insertTransaction(transaction: ScheduledTransactionEntity) {
        val db = AppDatabase.getInstance(applicationContext)
        db.getScheduledTransactionDao().insertTransaction(transaction)
    }

    companion object {

        fun getData(transactionEntity: ScheduledTransactionEntity): Data {
            return Data.Builder()
                    .putLong(ARG_ID, transactionEntity.transactionId)
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
    // OneTimeWorkRequest is used because of some reasons:
    // 1. Max TimeUnit constant is DAYS (it isn't suitable to months and years
    // with different number of days)
    val workRequest = OneTimeWorkRequest.Builder(ScheduledTransactionWorker::class.java)
            .setInitialDelay(ScheduledTransactionWorker.getDelay(transaction), TimeUnit.MILLISECONDS)
            .setInputData(ScheduledTransactionWorker.getData(transaction))
            .build()
    WorkManager.getInstance().enqueue(workRequest)
}