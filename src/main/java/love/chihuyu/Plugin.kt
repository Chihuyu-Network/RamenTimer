package love.chihuyu

import love.chihuyu.commands.CommandTimer
import love.chihuyu.data.ConfigContainer
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin(), Listener {

    companion object {
        lateinit var plugin: JavaPlugin
        val prefix = "${ChatColor.GOLD}[RT]${ChatColor.RESET}"
    }

    init {
        plugin = this
    }

    override fun onEnable() {
        saveDefaultConfig()

        CommandTimer.main.register()

        server.pluginManager.registerEvents(this, this)

        ConfigContainer.init()
    }


}