package tees.data.remote

import okhttp3.OkHttpClient
import org.koin.dsl.module

const val QWERTEE_URL_CONFIG_FIELD_KEY = "qwertee-url-property"

val teesRemoteDataModule = module {
    factory { QwerteeConfig(getProperty(QWERTEE_URL_CONFIG_FIELD_KEY)) }
    factory { OkHttpClient.Builder().build() }
    factory { JsoupHtmlParser() }
    factory { QwerteeWebScrapper(get(), get(), get()) }
    factory { TeesRemoteDataSource(get()) }
}