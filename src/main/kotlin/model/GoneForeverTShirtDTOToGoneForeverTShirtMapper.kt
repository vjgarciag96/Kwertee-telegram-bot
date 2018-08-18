package model

class GoneForeverTShirtDTOToGoneForeverTShirtMapper {

    private fun map(toMap: GoneForeverTShirtDTO): GoneForeverTShirt = GoneForeverTShirt(
            toMap.eurPrice.plus("€"),
            toMap.usdPrice.plus("\$"),
            toMap.gbpPrice.plus("£"),
            toMap.title,
            "https:${toMap.imageUrl}"
    )

    fun map(toMap: List<GoneForeverTShirtDTO>): List<GoneForeverTShirt> {
        val mappedTShirts = arrayListOf<GoneForeverTShirt>()
        toMap.forEach { mappedTShirts.add(map(it)) }
        return mappedTShirts
    }
}