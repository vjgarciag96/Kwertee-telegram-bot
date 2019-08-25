package publications.domain

import org.koin.dsl.module

val publicationsDomainModule = module {
    factory { GetLastPublicationInfo(get()) }
    factory { IsNeededToPublish(get(), get()) }
    factory { SetLastPublicationTimeToLive(get()) }
}