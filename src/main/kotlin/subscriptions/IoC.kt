package subscriptions

import subscriptions.data.local.subscriptionsLocalDataModule
import subscriptions.data.repository.subscriptionsRepositoryModule
import subscriptions.domain.subscriptionsDomainModule

val subscriptionsModules =
    subscriptionsLocalDataModule +
        subscriptionsRepositoryModule +
        subscriptionsDomainModule