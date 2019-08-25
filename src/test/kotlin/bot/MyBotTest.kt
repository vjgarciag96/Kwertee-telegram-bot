package bot

import io.mockk.mockk
import io.mockk.verify
import me.ivmg.telegram.Bot
import org.junit.Test

class MyBotTest {

    private val bot = mockk<Bot>(relaxed = true)

    private val myBot: MyBot = MyBot(bot)

    @Test
    fun `Should start polling when 'runWithPolling()' is called`() {
        whenRunWithPollingIsCalled()

        thenBotStartsPolling()
    }

    private fun whenRunWithPollingIsCalled() {
        myBot.runWithPolling()
    }

    private fun thenBotStartsPolling() {
        verify { bot.startPolling() }
    }
}