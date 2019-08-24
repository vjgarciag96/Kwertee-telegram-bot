package publications

import org.koin.dsl.module

val publicationsModule = module {
    single { PublicationsRepository(get(), get()) }
}