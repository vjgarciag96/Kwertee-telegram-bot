package tees.data.remote

class TeesRemoteDataSource(private val webScrapper: QwerteeWebScrapper) {

    suspend fun fetchGoneForever(): List<TeeDto> = webScrapper.getGoneForeverTShirts()

    suspend fun fetchLastChance(): List<TeeDto> = webScrapper.getLastChanceTees()
}