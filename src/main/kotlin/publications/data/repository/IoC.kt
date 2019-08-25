package publications.data.repository

import org.koin.dsl.module

val publicationsRepositoryDataModule = module {
    single { PublicationsRepository(get()) }
}