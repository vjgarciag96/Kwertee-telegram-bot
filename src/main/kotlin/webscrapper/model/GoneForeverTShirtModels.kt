package webscrapper.model

data class GoneForeverTShirtDTO(val eurPrice: String,
                                val usdPrice: String,
                                val gbpPrice: String,
                                val tShirtTitle: String,
                                val tShirtImageUrl: String)

typealias GoneForeverTShirt = GoneForeverTShirtDTO