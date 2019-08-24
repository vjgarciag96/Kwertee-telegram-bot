package tees.domain

import org.koin.dsl.module

val teesDomainModule = module {
    factory { FetchGoneForeverTees(get()) }
    factory { TeesBL(get(), get()) }
    factory { FetchPromotedTees(get()) }
    factory { FetchTeesTask(get(), get(), get()) }
    factory { PublishFreshTeesTask(get(), get(), get(), get(), get()) }
    factory { PublishGoneForeverTees(get(), get()) }
    factory { PublishLastChanceTees(get(), get()) }
    factory { PublishPromotedTees(get(), get()) }
}