package subscriptions.data.repository

import org.koin.dsl.module

val subscriptionsRepositoryModule = module {
    single { SubscriptionsRepository(get()) }
}