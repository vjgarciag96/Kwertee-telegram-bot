package subscriptions.data.local

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val subscriptionsLocalDataModule = module {
    single {
        // Provides SQLite in file DB
        Database.connect("jdbc:sqlite:src/main/resources/qwertee.db", "org.sqlite.JDBC")
    }
    factory { SetUpDatabase(get()) }
    factory { SubscriptionsLocalDataSource() }
}