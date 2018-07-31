package com.zavijavasoft.yafina.stub

import com.zavijavasoft.yafina.model.AccountEntity
import com.zavijavasoft.yafina.model.AccountsStorage
import io.reactivex.Single
import java.io.InvalidObjectException

class StubAccountsStorageImpl : AccountsStorage {

    val list = mutableListOf<AccountEntity>()

    init {
        list.add(AccountEntity(1, "RUR", "Наличные в рублях", "Все, что есть по карманам"))
        list.add(AccountEntity(2, "USD", "Наличные в долларах", "Все, что есть по карманам"))
        list.add(AccountEntity(3, "USD", "Дебетовая карта", "Сбербанк VISA"))
        list.add(AccountEntity(4, "RUR", "Кредитная карта", "ВТБ Mastercard"))
        list.add(AccountEntity(5, "RUR", "Яндекс.Деньги", "Счет на Яндекс.Деньги"))
        list.add(AccountEntity(6, "USD", "PayPal", "Счет на PayPal"))
    }

    override fun getAccounts(): Single<List<AccountEntity>> {
        return Single.just(list)

    }

    override fun getAccountById(id: Long): Single<AccountEntity> {
        val item = list.find { it -> it.id == id }
        if (item != null) {
            return Single.just(item)
        }
        return Single.error(InvalidObjectException("no such article id"))
    }
}