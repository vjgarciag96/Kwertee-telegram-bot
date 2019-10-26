[![CircleCI](https://circleci.com/gh/vjgarciag96/kwertee-telegram-bot.svg?style=svg)](https://circleci.com/gh/vjgarciag96/kwertee-telegram-bot)
# kwertee-telegram-bot
Bot to get information about promoted tees available on http://www.qwertee.com.

The connection with Telegram's Bot API is performed with https://github.com/seik/kotlin-telegram-bot library

You can test the current bot's version in https://t.me/qwertee_tshirts_bot

<img src="https://github.com/vjgarciag96/kwertee-telegram-bot/blob/master/doc/promoted_command.jpg" width="100" height="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://github.com/vjgarciag96/kwertee-telegram-bot/blob/master/doc/command_list.jpg" width="100" height="200">

## Running the bot locally
Edit *src/main/resources/koin.properties* file to set your own bot token on **bot-token-property**

Execute the *runApp* gradle task

````
./gradlew runApp
````

That's all!

## Architecture

TBD

