package webscrapper.mapper

import webscrapper.model.GoneForeverTShirt
import webscrapper.model.GoneForeverTShirtDTO

class GoneForeverTShirtDTOToGoneForeverTShirtMapper {

    private fun map(toMap: GoneForeverTShirtDTO): GoneForeverTShirt = GoneForeverTShirt(
            toMap.eurPrice.plus("€"),
            toMap.usdPrice.plus("\$"),
            toMap.gbpPrice.plus("£"),
            toMap.tShirtTitle,
            "https:${toMap.tShirtImageUrl}"
    )

    fun map(toMap: List<GoneForeverTShirtDTO>): List<GoneForeverTShirt> {
        val mappedTShirts = arrayListOf<GoneForeverTShirt>()
        toMap.forEach { mappedTShirts.add(map(it)) }
        return mappedTShirts
    }
}