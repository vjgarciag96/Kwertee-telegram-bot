package domain

import io.reactivex.Observable
import model.GoneForeverTShirtDTOToGoneForeverTShirtMapper
import model.TShirt
import webscrapper.QwerteeWebScrapper

class GetGoneForeverTShirts(
    private val webScrapper: QwerteeWebScrapper,
    private val mapper: GoneForeverTShirtDTOToGoneForeverTShirtMapper
) {

    operator fun invoke(): Observable<List<TShirt>> =
            webScrapper.getGoneForeverTShirts().map { mapper.map(it) }
}