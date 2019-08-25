package publications.data

import org.koin.dsl.module

val publicationsDataModule = module {
    single { PublicationsRepository(get()) }
}