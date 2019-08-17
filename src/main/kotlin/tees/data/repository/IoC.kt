package tees.data.repository

import org.koin.dsl.module

val teesRepositoryModule = module {
    single { TeesRepository(get()) }
}