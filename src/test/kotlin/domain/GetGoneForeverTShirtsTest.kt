package domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import model.GoneForeverTShirt
import model.GoneForeverTShirtDTOToGoneForeverTShirtMapper
import org.junit.Test
import webscrapper.QwerteeWebScrapper

class GetGoneForeverTShirtsTest {

    private val webScrapper: QwerteeWebScrapper = mock()
    private val mapper: GoneForeverTShirtDTOToGoneForeverTShirtMapper = mock()

    private val getGoneForeverTShirts = GetGoneForeverTShirts(webScrapper, mapper)

    @Test
    fun `Should use web scrapper for getting gone forever t-shirts when is invoked`() {
        givenAMockedWebScrapper()

        whenGetGoneForeverTShirtsIsInvoked()

        thenWebScrapperIsCalledForGettingTShirtsInfo()
    }

    @Test
    fun `Should map the web scrapper result from list of GoneForeverTShirtDto to list of GoneForeverTShirt`() {
        givenAMockedWebScrapper()

        whenGetGoneForeverTShirtsIsInvoked()

        thenResultIsMapped()
    }

    private fun givenAMockedWebScrapper() {
        whenever(webScrapper.getGoneForeverTShirts())
                .thenReturn(Observable.just(GoneForeverTShirtDTOProvider.testTShirtSet1()))
    }

    private fun whenGetGoneForeverTShirtsIsInvoked() {
        val testSubscriber = TestObserver<List<GoneForeverTShirt>>()
        val tShirtsObservable = getGoneForeverTShirts.invoke()

        tShirtsObservable.subscribe(testSubscriber)
    }

    private fun thenWebScrapperIsCalledForGettingTShirtsInfo() {
        verify(webScrapper).getGoneForeverTShirts()
    }

    private fun thenResultIsMapped() {
        verify(mapper).map(GoneForeverTShirtDTOProvider.testTShirtSet1())
    }
}