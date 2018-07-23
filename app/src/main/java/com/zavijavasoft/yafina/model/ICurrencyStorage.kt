package com.zavijavasoft.yafina.model

interface ICurrencyStorage {
    fun getCurrencyList(): List<String>
    fun addCurrency(currency: String)
    fun removeCurrency(currency: String)
}

