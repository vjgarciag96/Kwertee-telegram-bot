package data.local.exposed

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class SetUpDatabase(
    private val database: Database
) {

    operator fun invoke() {
        transaction(database) {
            SchemaUtils.create(SubscriptionTable)
        }
    }
}