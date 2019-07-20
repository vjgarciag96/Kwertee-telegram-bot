package model

class GoneForeverTShirtDTOToGoneForeverTShirtMapper {

    private fun map(toMap: TShirtDTO): TShirt = TShirt(
        toMap.eurPrice.plus("€"),
        toMap.usdPrice.plus("\$"),
        toMap.gbpPrice.plus("£"),
        toMap.title,
        toMap.imageUrl
    )

    fun map(toMap: List<TShirtDTO>): List<TShirt> {
        val mappedTShirts = arrayListOf<TShirt>()
        toMap.forEach { mappedTShirts.add(map(it)) }
        return mappedTShirts
    }
}