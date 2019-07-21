package domain

import com.nhaarman.mockitokotlin2.mock
import data.remote.QwerteeWebScrapper
import org.junit.Test

class GetGoneForeverTShirtsTest {

    private val webScrapper: QwerteeWebScrapper = mock()

    //private val getGoneForeverTShirts = GetGoneForeverTShirts(webScrapper)

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
    }

    private fun givenAMockedWebScrapper() {
        //whenever(webScrapper.getGoneForeverTShirts())
        //  .thenReturn(Observable.just(GoneForeverTShirtDTOProvider.testTShirtSet1()))
    }

    private fun whenGetGoneForeverTShirtsIsInvoked() {
        //val tShirtsObservable = getGoneForeverTShirts.invoke()

        //tShirtsObservable.subscribe(testSubscriber)
    }

    private fun thenWebScrapperIsCalledForGettingTShirtsInfo() {
        //verify(webScrapper).getGoneForeverTShirts()
    }
}