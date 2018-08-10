package com.zavijavasoft.yafina.services

import com.zavijavasoft.yafina.model.CurrencyExchangeRatio
import com.zavijavasoft.yafina.model.CurrencyExchangeRatioStorage
import com.zavijavasoft.yafina.model.CurrencyMonitor
import com.zavijavasoft.yafina.model.CurrencyStorage
import io.reactivex.Single
import okhttp3.OkHttpClient
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import java.util.*
import javax.inject.Inject


private const val HALF_DAY = 1000 * 60 * 60 * 12

/*

http://www.cbr.ru/scripts/XML_daily.asp?
<ValCurs Date="28.07.2018" name="Foreign Currency Market">
    <Valute ID="R01010">
        <NumCode>036</NumCode>
        <CharCode>AUD</CharCode>
        <Nominal>1</Nominal>
        <Name>Австралийский доллар</Name>
        <Value>46,4549</Value>
    </Valute>
*/
@Root(name = "ValCurs")
data class CbrDataList(
        @field:Attribute(name = "Date")
        var date: String? = null,

        @field:Attribute(required = false)
        var name: String? = null,

        @field:ElementList(inline = true, required = false)
        var list: List<CbrDataItem>? = null
)

@Root(name = "Valute")
data class CbrDataItem(
        @field:Attribute(required = false, name = "ID")
        var id: String? = null,

        @field:Element(name = "NumCode", required = false)
        var numCode: String? = null,
        @field:Element(name = "CharCode")
        var charCode: String? = null,
        @field:Element(name = "Nominal")
        var nominal: String? = null,
        @field:Element(name = "Name")
        var name: String? = null,
        @field:Element(name = "Value")
        var value: String? = null
)

interface CbrApi {
    @GET("/scripts/XML_daily.asp")
    fun getCurrenciesStatus(): Single<CbrDataList>
}

class CbrCurrencyMonitorImpl @Inject constructor(
        private val currencyStorage: CurrencyStorage,
        private val currencyExchangeRatioStorage: CurrencyExchangeRatioStorage
) : CurrencyMonitor {

    private val retrofit = Retrofit.Builder()
            .baseUrl("http://www.cbr.ru")
            .client(OkHttpClient())
            .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(Persister(AnnotationStrategy())))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    private val api = retrofit.create(CbrApi::class.java)

    var listCurrencyExchangeRatio = listOf<CurrencyExchangeRatio>()

    override fun pull(): Single<List<CurrencyExchangeRatio>> {
        return Single.fromCallable<List<CurrencyExchangeRatio>> {
            val lastUpdatedRatios = currencyExchangeRatioStorage.getLastUpdatedCurrencies().toMutableList()
            val lastUpdate = lastUpdatedRatios.maxBy { it.requestTime }?.requestTime?.time ?: 0
            val currencies = currencyStorage.getCurrencyList().blockingGet().toSet()
            if (Date().time - lastUpdate > HALF_DAY) {
                lastUpdatedRatios.clear()
                val response = api.getCurrenciesStatus()
                val pack: CbrDataList = response.blockingGet()
                for (valute in pack.list ?: listOf()) {
                    if (currencies.contains(valute.charCode)) {
                        val nominal = (valute.nominal ?: "1.0").toFloat()
                        val value = (valute.value ?: "1.0").replace(",", ".").toFloat()
                        val charCode = valute.charCode ?: "UNK"
                        lastUpdatedRatios.add(CurrencyExchangeRatio(0, charCode, "RUR",
                                (value / nominal), Date()))
                        lastUpdatedRatios.add(CurrencyExchangeRatio(0, "RUR", charCode,
                                (nominal / value), Date()))
                    }
                }
                currencyExchangeRatioStorage.insertCurrencyRatios(lastUpdatedRatios)
            }
            listCurrencyExchangeRatio = lastUpdatedRatios
            lastUpdatedRatios
        }

    }
}