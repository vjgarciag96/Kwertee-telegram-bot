package domain

import io.reactivex.Observable
import webscrapper.QwerteeWebScrapper
import model.GoneForeverTShirtDTOToGoneForeverTShirtMapper
import model.GoneForeverTShirt

class GetGoneForeverTShirts(private val webScrapper: QwerteeWebScrapper,
                            private val mapper: GoneForeverTShirtDTOToGoneForeverTShirtMapper) {
    fun invoke(): Observable<List<GoneForeverTShirt>> =
            webScrapper.getGoneForeverTShirts().map { mapper.map(it) }
}