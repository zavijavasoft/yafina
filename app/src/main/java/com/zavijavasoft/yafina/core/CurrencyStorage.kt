package com.zavijavasoft.yafina.core

interface CurrencyStorage {
    fun getCurrencyList(): List<String>
    fun addCurrency(currency: String)
    fun removeCurrency(currency: String)
}

