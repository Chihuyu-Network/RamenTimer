package love.chihuyu.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.GreedyStringArgument
import dev.jorel.commandapi.arguments.LongArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import love.chihuyu.Plugin.Companion.plugin
import love.chihuyu.Plugin.Companion.prefix
import love.chihuyu.TimerManager
import love.chihuyu.data.TimerStorage
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor

object TimerStart {

    val main: CommandAPICommand = CommandAPICommand("start")
        .withPermission("ramentimer.start")
        .withArguments(LongArgument("seconds"), GreedyStringArgument("title"))
        .executesPlayer(
            PlayerCommandExecutor { sender, args ->
                val length = args[0] as Long
                val title = args[1] as String

                if (title in TimerStorage.timers.map { it.title }) {
                    sender.sendMessage("$prefix ${ChatColor.RED}Timer already exists.")
                    return@PlayerCommandExecutor
                }

                TimerManager.start(sender, length, title)

                plugin.server.broadcast(Component.text("$prefix Timer ${ChatColor.GREEN}\"${title}\"${ChatColor.RESET} started."))
                sender.sendMessage("$prefix to stop timer, please type ${ChatColor.UNDERLINE}/ramentimer end ${title}${ChatColor.RESET}.")
            }
        )
}