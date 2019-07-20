import java.util.*

class FetchGoneForeverTShirts(
    private val fetchGoneForeverTShirtsTask: FetchGoneForeverTShirtsTask
) {

    operator fun invoke() {
        val timer = Timer()
        timer.scheduleAtFixedRate(
            fetchGoneForeverTShirtsTask,
            0,
            10000
        )
    }
}