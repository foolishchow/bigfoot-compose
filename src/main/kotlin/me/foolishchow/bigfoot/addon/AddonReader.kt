package me.foolishchow.bigfoot.addon

import com.google.gson.Gson
import java.io.File


const val commonNormal = "_retail_/Interface/AddOns"
const val commonPath = "/Applications/World of Warcraft/"

class Addon(
    var name: String,
    var Interface: String = "",
    var version: String = "",
    var reversion: String = "",
    var child: List<Addon>? = null
) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}


fun readAddon() {
    val installRoot = File(commonPath)
    if (!installRoot.exists()) {
        return
    }
    val addonRoot = File(installRoot, commonNormal)
    if (!addonRoot.exists()) {
        return
    }
    if (!addonRoot.isDirectory) {
        return
    }
    val addons = mutableListOf<Addon>()
    addonRoot.list()?.forEach { dirname ->
        val dir = File(addonRoot, dirname)
        val toc = File(dir, "${dirname}.toc")
        if (toc.exists()) {
            var version = ""
            var reversion = ""
            var Interface = ""
            for (readLine in toc.readLines()) {
                if (readLine.trim().startsWith("## Interface:")) {
                    Interface = readLine.replace("## Interface:", "").trim()
                }

                if (readLine.trim().startsWith("## Version:")) {
                    version = readLine.replace("## Version:", "").trim()
                }
                if (readLine.trim().startsWith("## X-Revision")) {
                    reversion = readLine.replace("## X-Revision:", "").trim()
                }
            }
            if (reversion == dirname) {
                reversion = ""
            }
            addons.add(Addon(dirname, Interface, version, reversion))
        }
    }

    judgeBigFoot(addonRoot, addons).forEach { addon ->
        println("${addon.name} @ ${addon.version}")
    }
}



