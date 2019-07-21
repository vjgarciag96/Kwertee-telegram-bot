import data.remote.TShirtDTO
import java.util.*

class GoneForeverTShirtDTOProvider {
    companion object {
        private fun testTShirt1(): TShirtDTO = TShirtDTO(
                "5",
                "10",
                "12",
                "TrollShirt",
                "http://trollshirt.com/roto2"
        )

        fun testTShirtSet1(): List<TShirtDTO> =
                Collections.singletonList(testTShirt1())
    }
}