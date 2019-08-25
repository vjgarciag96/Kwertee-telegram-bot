package core

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val coreModule = module {
    single {
        // Provides SQLite in file DB
        Database.connect(
            url = "jdbc:sqlite:src/main/resources/qwertee.db",
            driver = "org.sqlite.JDBC"
        )
    }
    factory { SetUpDatabase(get()) }
    factory { TimeProvider() }
    factory { ExecutorServiceFactory() }
}