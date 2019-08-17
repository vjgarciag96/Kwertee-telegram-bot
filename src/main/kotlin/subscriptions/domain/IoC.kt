package subscriptions.domain

import org.koin.dsl.module

val subscriptionsDomainModule = module {
    factory { Subscribe(get()) }
    factory { Unsubscribe(get()) }
    factory { GetSubscriptions(get()) }
}