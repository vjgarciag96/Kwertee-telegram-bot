import java.util.*

class FetchGoneForeverTShirts(
    private val fetchGoneForeverTShirtsTask: FetchGoneForeverTShirtsTask
) {

    operator fun invoke() {
        Timer().scheduleAtFixedRate(
            fetchGoneForeverTShirtsTask,
            0,
            10000
        )
    }
}