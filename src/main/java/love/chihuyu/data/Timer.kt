package love.chihuyu.data

import org.bukkit.OfflinePlayer
import org.bukkit.scheduler.BukkitTask

class Timer(
    val owner: OfflinePlayer,
    val title: String,
    var tasks: MutableList<BukkitTask>,
) {
}