package love.chihuyu.commands

import dev.jorel.commandapi.CommandAPICommand

object CommandTimer {

    val main: CommandAPICommand = CommandAPICommand("ramentimer")
        .withPermission("ramentimer.ramentimer")
        .withSubcommands(
            TimerStart.main,
            TimerEnd.main,
            TimerReload.main
        )
}
