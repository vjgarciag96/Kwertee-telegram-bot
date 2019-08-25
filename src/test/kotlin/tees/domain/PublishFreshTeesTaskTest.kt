package tees.domain

import core.ExecutorServiceFactory
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import publications.domain.GetLastPublicationInfo
import publications.domain.IsNeededToPublish
import publications.domain.Publication
import subscriptions.domain.GetSubscriptions
import java.util.concurrent.Callable
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class PublishFreshTeesTaskTest {

    private val fetchPromotedTeesMock = mockk<FetchPromotedTees>()
    private val getLastPublicationsInfoMock = mockk<GetLastPublicationInfo>()
    private val getSubscriptionsMock = mockk<GetSubscriptions>()
    private val publishPromotedTeesMock = mockk<PublishPromotedTees>(relaxed = true)
    private val isNeededToPublishMock = mockk<IsNeededToPublish>()
    private val timeToLiveHandlerMock = mockk<TimeToLiveHandler>()
    private val scheduledExecutorServiceMock = mockk<ScheduledExecutorService>(relaxed = true)
    private val executorServiceFactoryMock = mockk<ExecutorServiceFactory> {
        every { newSingleThreadScheduledThreadPool() } returns scheduledExecutorServiceMock
    }

    private val sut = PublishFreshTeesTask(
        fetchPromotedTees = fetchPromotedTeesMock,
        getLastPublicationInfo = getLastPublicationsInfoMock,
        getSubscriptions = getSubscriptionsMock,
        publishPromotedTees = publishPromotedTeesMock,
        isNeededToPublish = isNeededToPublishMock,
        timeToLiveHandler = timeToLiveHandlerMock,
        executorServiceFactory = executorServiceFactoryMock
    )

    @Test
    fun `Task is scheduled properly`() = runBlockingTest {
        coEvery { isNeededToPublishMock() } returns false
        coEvery { getLastPublicationsInfoMock() } returns Publication(ANY_TIMESTAMP, ANY_TIME_TO_LIVE)
        coEvery { timeToLiveHandlerMock.delayUntilExpiration(ANY_TIMESTAMP, ANY_TIME_TO_LIVE) } returns ANY_DELAY_UNTIL_EXPIRATION

        sut.invoke()

        verify { scheduledExecutorServiceMock.schedule(any<Callable<Job>>(), ANY_DELAY_UNTIL_EXPIRATION, TimeUnit.MILLISECONDS) }
    }

    private companion object {
        const val ANY_TIMESTAMP = 343344332L
        const val ANY_TIME_TO_LIVE = 23434324
        const val ANY_DELAY_UNTIL_EXPIRATION = 12L
    }
}