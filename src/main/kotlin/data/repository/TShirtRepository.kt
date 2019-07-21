package data.repository

import data.remote.QwerteeWebScrapper
import domain.TShirt

class TShirtRepository(private val qwerteeWebScrapper: QwerteeWebScrapper) {

    suspend fun fetchGoneForever(): List<TShirt> = qwerteeWebScrapper.getGoneForeverTShirts()
}