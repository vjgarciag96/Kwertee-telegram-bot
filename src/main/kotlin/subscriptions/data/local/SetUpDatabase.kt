package subscriptions.data.local

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import subscriptions.data.local.exposed.SubscriptionTable

class SetUpDatabase(
    private val database: Database
) {

    operator fun invoke() {
        transaction(database) {
            SchemaUtils.create(SubscriptionTable)
        }
    }
}