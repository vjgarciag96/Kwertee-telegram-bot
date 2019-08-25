package tees.domain

import core.ExecutorServiceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import publications.domain.GetLastPublicationInfo
import publications.domain.IsNeededToPublish
import subscriptions.domain.GetSubscriptions
import java.util.concurrent.TimeUnit

class PublishFreshTeesTask(
    private val fetchPromotedTees: FetchPromotedTees,
    private val getLastPublicationInfo: GetLastPublicationInfo,
    private val getSubscriptions: GetSubscriptions,
    private val publishPromotedTees: PublishPromotedTees,
    private val isNeededToPublish: IsNeededToPublish,
    private val timeToLiveHandler: TimeToLiveHandler,
    executorServiceFactory: ExecutorServiceFactory
) {

    private val scheduler = executorServiceFactory.newSingleThreadScheduledThreadPool()

    operator fun invoke() = CoroutineScope(Dispatchers.IO).launch {
        if (isNeededToPublish()) {
            publishPromotedTees()
        }

        scheduleNextPromotedTeesPublication()
    }

    private suspend fun publishPromotedTees() {
        val subscriptions = getSubscriptions()
        val promotedTees = fetchPromotedTees()

        subscriptions.forEach { subscription ->
            publishPromotedTees(subscription.userId, promotedTees)
        }
    }

    private suspend fun scheduleNextPromotedTeesPublication() {
        val delayToFetchNewPromotedTees = getDelayToFetchNewPromotedTees()
        scheduler.schedule(::invoke, delayToFetchNewPromotedTees, TimeUnit.MILLISECONDS)
    }

    private suspend fun getDelayToFetchNewPromotedTees(): Long {
        val lastPublicationInfo = getLastPublicationInfo()

        return if (lastPublicationInfo == null) {
            0
        } else {
            timeToLiveHandler.delayUntilExpiration(
                lastPublicationInfo.timestamp,
                lastPublicationInfo.timeToLive
            )
        }
    }
}