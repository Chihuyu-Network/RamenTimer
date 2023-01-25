package love.chihuyu.data

import org.bukkit.OfflinePlayer

object TimerStorage {

    val timers = mutableSetOf<Timer>()

    fun getTimerOrNullByOwner(offlinePlayer: OfflinePlayer): Timer? {
        return timers.firstOrNull { it.owner == offlinePlayer }
    }

    fun getTimerOrNullByTitle(title: String): Timer? {
        return timers.firstOrNull { it.title == title }
    }
}
