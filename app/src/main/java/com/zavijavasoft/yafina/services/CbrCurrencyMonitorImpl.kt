package com.zavijavasoft.yafina.services

import com.zavijavasoft.yafina.model.CurrencyExchangeRatio
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

class CbrCurrencyMonitorImpl @Inject constructor(private val currencyStorage: CurrencyStorage) : CurrencyMonitor {

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
            // FIXME: makes too many requests
//            val response = api.getCurrenciesStatus()
//            val pack: CbrDataList = response.blockingGet()
            val pack = CbrDataList()
            val currencies = currencyStorage.getCurrencyList().blockingGet().toSet()
            val listOut = mutableListOf<CurrencyExchangeRatio>()

            val listIn: List<CbrDataItem> = pack.list ?: listOf()
            for (valute in listIn) {
                if (currencies.contains(valute.charCode)) {
                    val nominal = (valute.nominal ?: "1.0").toFloat()
                    val value = (valute.value ?: "1.0").replace(",", ".").toFloat()
                    val charCode = valute.charCode ?: "UNK"
                    listOut.add(CurrencyExchangeRatio(charCode, "RUR",
                            (value / nominal), Date()))
                    listOut.add(CurrencyExchangeRatio("RUR", charCode,
                            (nominal / value), Date()))
                }
            }

            listCurrencyExchangeRatio = listOut
            listOut
        }
    }
}