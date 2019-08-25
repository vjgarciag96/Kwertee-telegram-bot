package publications.data.repository

import publications.data.local.PublicationsLocalDataSource

class PublicationsRepository(
    private val publicationsLocalDataSource: PublicationsLocalDataSource
) {

    fun getLastPublicationInfo(): PublicationData? = publicationsLocalDataSource.getLastPublicationInfo()

    fun setLastPublicationTimeToLive(timeToLive: Int) {
        publicationsLocalDataSource.setLastPublicationTimeToLive(timeToLive)
    }
}