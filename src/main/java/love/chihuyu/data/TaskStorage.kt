package love.chihuyu.data

import org.bukkit.scheduler.BukkitTask

object TaskStorage {

    val timers = hashMapOf<String, MutableList<BukkitTask>>()
}
