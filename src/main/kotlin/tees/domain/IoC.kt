package tees.domain

import org.koin.dsl.module

val teesDomainModule = module {
    factory { TeesBL(get(), get()) }
    factory { FetchGoneForeverTees(get()) }
    factory { FetchLastChanceTees(get()) }
    factory { FetchPromotedTees(get()) }
    factory { FetchTeesTask(get(), get(), get()) }
    factory { PublishFreshTeesTask(get(), get(), get(), get(), get(), get(), get()) }
    factory { PublishGoneForeverTees(get(), get()) }
    factory { PublishLastChanceTees(get(), get()) }
    factory { PublishPromotedTees(get(), get()) }
    factory { TimeToLiveHandler(get()) }
}