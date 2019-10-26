package bot.commands

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Message
import tees.domain.FetchLastChanceTees
import tees.domain.toTextMessage

class LastChanceTeesCommand(private val fetchLastChanceTees: FetchLastChanceTees) : BotCommand {
    override val name: String
        get() = "lastchance"

    override fun action(bot: Bot, command: Message, args: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val tShirts = fetchLastChanceTees()
            tShirts.forEach { tShirt ->
                bot.sendPhoto(command.chat.id, tShirt.imageUrl, tShirt.toTextMessage())
            }
        }
    }
}