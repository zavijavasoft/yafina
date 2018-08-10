package com.zavijavasoft.yafina.ui.balance

import java.util.*

interface BalancePresenter {
    fun needUpdatePieCurrenciesBetween(dateFrom: Date, dateTo: Date)
    fun needUpdateBarSpendingBetween(dateFrom: Date, dateTo: Date)
}