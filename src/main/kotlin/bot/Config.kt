package bot

import org.yaml.snakeyaml.Yaml
import java.io.File

internal class BotTokenNotFoundException : Exception("You must add your bot token on bot_config.yml file as bot_token")

object Config {
    val BOT_TOKEN: String by lazy {
        val configMap: Map<String, String> =
                Yaml().load(File("${System.getProperty("user.dir")}/config/bot_config.yml").inputStream())
        return@lazy configMap["bot_token"]
                ?: throw BotTokenNotFoundException()
    }
}