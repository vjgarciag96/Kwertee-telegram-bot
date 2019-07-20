package domain

import data.TShirtDTO
import data.toDomainModel
import io.reactivex.Observable
import mapList
import data.webscrapper.QwerteeWebScrapper

class GetGoneForeverTShirts(
    private val webScrapper: QwerteeWebScrapper
) {

    operator fun invoke(): Observable<List<TShirt>> =
        webScrapper.getGoneForeverTShirts().mapList(TShirtDTO::toDomainModel)
}