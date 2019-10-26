package bot.commands

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Message
import tees.domain.FetchGoneForeverTees
import tees.domain.toTextMessage

class GoneForeverTeesCommand(private val fetchGoneForeverTees: FetchGoneForeverTees) : BotCommand {

    override val name: String
        get() = "goneforever"

    override fun action(bot: Bot, command: Message, args: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val tShirts = fetchGoneForeverTees()
            tShirts.forEach { tShirt ->
                bot.sendPhoto(command.chat.id, tShirt.imageUrl, tShirt.toTextMessage())
            }
        }
    }
}
