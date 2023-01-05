package love.chihuyu.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.CommandExecutor
import love.chihuyu.Plugin.Companion.prefix
import love.chihuyu.data.ConfigContainer

object TimerReload {

    val main = CommandAPICommand("reload")
        .withPermission("ramentimer.reload")
        .executes(
            CommandExecutor { sender, _ ->
                ConfigContainer.init()
                sender.sendMessage("$prefix Config reloaded.")
            }
        )
}