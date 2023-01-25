package love.chihuyu.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.GreedyStringArgument
import dev.jorel.commandapi.executors.CommandExecutor
import love.chihuyu.Plugin.Companion.prefix
import love.chihuyu.TimerManager
import love.chihuyu.data.TimerStorage
import org.bukkit.ChatColor
import java.util.concurrent.CompletableFuture

object TimerEnd {

    val main: CommandAPICommand = CommandAPICommand("end")
        .withPermission("ramentimer.end")
        .withArguments(GreedyStringArgument("title").replaceSuggestions(ArgumentSuggestions.strings {
            CompletableFuture.supplyAsync {
                TimerStorage.timers.map { it.title }.toTypedArray()
            }.get()
        }))
        .executes(
            CommandExecutor { sender, args ->
                val title = args[0] as String

                if (
                    title !in TimerStorage.timers.map { it.title } &&
                    (TimerStorage.getTimerOrNullByTitle(title)?.owner?.name == sender.name || sender.hasPermission("ramentimer.forceend"))
                    ) {
                    sender.sendMessage("$prefix ${ChatColor.RED}Timer not found.")
                    return@CommandExecutor
                }

                TimerManager.end(title)
            }
        )
}