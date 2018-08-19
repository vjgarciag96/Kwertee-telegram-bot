import model.GoneForeverTShirtDTO
import java.util.*

class GoneForeverTShirtDTOProvider {
    companion object {
        private fun testTShirt1(): GoneForeverTShirtDTO = GoneForeverTShirtDTO(
                "5",
                "10",
                "12",
                "TrollShirt",
                "http://trollshirt.com/roto2"
        )

        fun testTShirtSet1(): List<GoneForeverTShirtDTO> =
                Collections.singletonList(testTShirt1())
    }
}