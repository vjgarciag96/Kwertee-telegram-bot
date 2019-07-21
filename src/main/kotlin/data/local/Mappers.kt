package data.local

import domain.Subscription

fun SubscriptionDO.toDomain(): Subscription = Subscription(userId, username)