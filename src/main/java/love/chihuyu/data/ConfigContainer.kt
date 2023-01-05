package love.chihuyu.data

import love.chihuyu.Plugin.Companion.plugin

object ConfigContainer {

    var bossbar = false

    fun init() {
        bossbar = plugin.config.getBoolean("bossbar")
    }
}
