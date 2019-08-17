package tees.domain

import org.koin.dsl.module

val teesDomainModule = module {
    factory { FetchGoneForeverTees(get()) }
    factory { PublishGoneForeverTShirts(get()) }
    factory { FetchTeesTask(get(), get(), get()) }
    factory { ScheduleTeesFetching(get()) }
}