package tees.data.local

import org.koin.dsl.module

val teesLocalDataModule = module {
    factory { TeesLocalDataSource(get(), get()) }
}