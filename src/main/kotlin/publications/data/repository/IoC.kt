package publications.data.repository

import org.koin.dsl.module
import publications.data.repository.PublicationsRepository

val publicationsRepositoryDataModule = module {
    single { PublicationsRepository(get()) }
}