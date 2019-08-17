package data.remote

import domain.TShirt
import org.jsoup.nodes.Element

fun TShirtDTO.toDomainModel(): TShirt = TShirt(
    eurPrice.plus("€"),
    usdPrice.plus("\$"),
    gbpPrice.plus("£"),
    title,
    imageUrl
)

interface QwerteeTShirtMapper {

}