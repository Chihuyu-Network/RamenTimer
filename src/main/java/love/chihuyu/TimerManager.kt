package love.chihuyu

import love.chihuyu.Plugin.Companion.plugin
import love.chihuyu.data.ConfigContainer
import love.chihuyu.data.Timer
import love.chihuyu.data.TimerStorage
import love.chihuyu.utils.EpochUtil
import love.chihuyu.utils.runTaskLater
import love.chihuyu.utils.runTaskTimer
import net.kyori.adventure.text.Component
import org.bukkit.*
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import java.util.*

object TimerManager {

    fun start(owner: OfflinePlayer, length: Long, title: String) {
        val encodedTitle = UUID.nameUUIDFromBytes(title.encodeToByteArray())

        val start = EpochUtil.nowEpoch()
        val end = start + length
        val timer = Timer(owner, title, mutableListOf())

        if (ConfigContainer.bossbar) {
            val updateBossbar = plugin.runTaskTimer(0, 20) {
                val key = NamespacedKey(plugin, "ramen-$encodedTitle")
                val bossbar = Bukkit.getBossBar(key) ?: Bukkit.createBossBar(key, "Remain", BarColor.YELLOW, BarStyle.SOLID)
                val remains = end - EpochUtil.nowEpoch()

                bossbar.setTitle("$title ≫ ${EpochUtil.formatTime(remains)}")
                bossbar.isVisible = true
                bossbar.removeAll()
                bossbar.progress = remains * (1.0 / (end - start)).coerceIn(0.0..1.0)

                plugin.server.onlinePlayers.forEach {
                    bossbar.addPlayer(it)
                }
            }

            timer.tasks += updateBossbar
        }

        val endTimer = plugin.runTaskLater(length * 20) {
            end(title)
        }

        timer.tasks += endTimer
    }

    fun end(title: String) {
        val encodedTitle = UUID.nameUUIDFromBytes(title.encodeToByteArray())
        val timer = TimerStorage.getTimerOrNullByTitle(title) ?: return
        timer.tasks.forEach {
            it.cancel()
        }
        TimerStorage.timers -= timer

        val key = NamespacedKey(plugin, "ramen-$encodedTitle")
        val bossbar = Bukkit.getBossBar(key)

        bossbar?.removeAll()
        plugin.server.removeBossBar(key)

        plugin.server.broadcast(Component.text("${Plugin.prefix} Timer ${ChatColor.GREEN}\"${title}\"${ChatColor.RESET} stopped!"))
        plugin.server.onlinePlayers.forEach { it.playSound(it, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f) }
    }
}