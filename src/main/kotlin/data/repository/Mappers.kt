package data.repository

import data.local.SubscriptionDO
import domain.Subscription

fun Subscription.toDiskObject(): SubscriptionDO = SubscriptionDO(userId, username)