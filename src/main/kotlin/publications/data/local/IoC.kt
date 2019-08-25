package publications.data.local

import org.koin.dsl.module

val publicationsLocalDataModule = module {
    factory { PublicationsLocalDataSource(get()) }
}