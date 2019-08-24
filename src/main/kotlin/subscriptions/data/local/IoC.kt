package subscriptions.data.local

import org.koin.dsl.module

val subscriptionsLocalDataModule = module {
    factory { SubscriptionsLocalDataSource() }
}