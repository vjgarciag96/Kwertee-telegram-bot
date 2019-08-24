package core

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import subscriptions.data.local.exposed.SubscriptionTable
import tees.data.local.exposed.TeesTable

class SetUpDatabase(
    private val database: Database
) {

    operator fun invoke() {
        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(SubscriptionTable)
            SchemaUtils.createMissingTablesAndColumns(TeesTable)
        }
    }
}