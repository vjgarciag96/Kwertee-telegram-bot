package tees.data.remote

class TeesRemoteDataSource(private val webScrapper: QwerteeWebScrapper) {

    fun fetchGoneForever(): List<TeeDto> = webScrapper.getGoneForeverTShirts()

    fun fetchLastChance(): List<TeeDto> = webScrapper.getLastChanceTees()

    fun fetchPromoted(): PromotedTeesDto = webScrapper.fetchPromoted()
}