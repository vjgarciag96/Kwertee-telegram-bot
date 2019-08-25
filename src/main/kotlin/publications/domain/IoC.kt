package publications.domain

import org.koin.dsl.module

val publicationsDomainModule = module {
    factory { GetLastPublicationTimestamp(get()) }
    factory { GetLastPublicationTimeToLive(get()) }
    factory { IsNeededToPublish(get(), get()) }
    factory { SetLastPublicationTimeToLive(get()) }
}