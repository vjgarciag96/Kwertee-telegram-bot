package tees.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import publications.PublicationsRepository
import subscriptions.domain.GetSubscriptions
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class PublishFreshTeesTask(
    private val fetchPromotedTees: FetchPromotedTees,
    private val publicationsRepository: PublicationsRepository,
    private val getSubscriptions: GetSubscriptions,
    private val publishPromotedTees: PublishPromotedTees,
    private val timeToLiveHandler: TimeToLiveHandler
) {

    operator fun invoke() {
        if (publicationsRepository.isNeededToPublish()) {
            CoroutineScope(Dispatchers.IO).launch {
                publishPromotedTees()
                scheduleNextPromotedTeesPublication()
            }
            return
        }

        scheduleNextPromotedTeesPublication()
    }

    private suspend fun publishPromotedTees() {
        val subscriptions = getSubscriptions()
        val promotedTees = fetchPromotedTees()

        subscriptions.forEach { subscription ->
            publishPromotedTees(subscription.userId, promotedTees.goneForeverTees)
        }

    }

    private fun scheduleNextPromotedTeesPublication() {
        val delayToFetchNewPromotedTees = getDelayToFetchNewPromotedTees()
        val scheduler = Executors.newScheduledThreadPool(1)
        scheduler.schedule(::invoke, delayToFetchNewPromotedTees, TimeUnit.MILLISECONDS)
    }

    private fun getDelayToFetchNewPromotedTees(): Long {
        val lastPublicationTimeToLive = publicationsRepository.lastPublicationTimeToLive
        val lastPublicationTimestamp = publicationsRepository.lastPublicationTimestamp

        return if (lastPublicationTimestamp == null || lastPublicationTimeToLive == null) {
            0
        } else {
            timeToLiveHandler.delayUntilExpiration(lastPublicationTimestamp, lastPublicationTimeToLive)
        }
    }
}