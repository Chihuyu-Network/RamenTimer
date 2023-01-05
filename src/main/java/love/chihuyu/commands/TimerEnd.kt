package love.chihuyu.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.GreedyStringArgument
import dev.jorel.commandapi.executors.CommandExecutor
import love.chihuyu.Plugin.Companion.prefix
import love.chihuyu.TimerManager
import love.chihuyu.data.TaskStorage
import org.bukkit.ChatColor
import java.util.concurrent.CompletableFuture

object TimerEnd {

    val main: CommandAPICommand = CommandAPICommand("end")
        .withPermission("ramentimer.end")
        .withArguments(GreedyStringArgument("title").replaceSuggestions(ArgumentSuggestions.strings {
            CompletableFuture.supplyAsync {
                TaskStorage.timers.keys.toTypedArray()
            }.get()
        }))
        .executes(
            CommandExecutor { sender, args ->
                val title = args[0] as String

                if (title !in TaskStorage.timers) {
                    sender.sendMessage("$prefix ${ChatColor.RED}Timer not found.")
                    return@CommandExecutor
                }

                TimerManager.end(title)
            }
        )
}