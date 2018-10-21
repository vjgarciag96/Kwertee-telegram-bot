package data

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection


object Subscriptions : LongIdTable() {
    val username: Column<String> = varchar("username", 200)
}

class Subscription(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<data.Subscription>(Subscriptions)

    var username by Subscriptions.username
}

class LocalSubscriptionsDataSource {

    init {
        Database.connect("jdbc:sqlite:${System.getProperty("user.dir")}/subscriptions.db", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Subscriptions)
        }
    }

    fun save(subscription: model.Subscription) {
        transaction {
            Subscription.new(subscription.userId) {
                username = subscription.username
            }
        }
    }
}
