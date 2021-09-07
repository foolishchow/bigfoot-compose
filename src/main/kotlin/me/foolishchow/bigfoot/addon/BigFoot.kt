package me.foolishchow.bigfoot.addon

import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.JsePlatform
import java.io.File

fun judgeBigFoot(
    addonRoot: File,
    addons: List<Addon>
): List<Addon> {
    val bigFoot = addons.find { it.name == "BigFoot" } ?: return addons
    val list = mutableListOf<Addon>()
    bigFoot.version = getBifFootVersion(File(addonRoot, "BigFoot"))
    val child = mutableListOf<Addon>()
    addons.forEach { addon ->
        if (addon.reversion == bigFoot.name) {
            child.add(addon)
        } else {
            list.add(addon)
        }
    }
    bigFoot.child = child
    return list
}

const val TEMPLATE = """
function GetLocale()
    return "zhCN"
end
function BigFootChangelog_ah()

end

function BigFootChangelog_at(a,b)

end

function BigFootChangelog_af()

end

function BigFootChangelog_ar()

end
"""

const val TEMP_END = """
function Version()
    return main..minor
end  
"""

fun getBifFootVersion(addonRoot: File): String {

    val listOf = listOf("Version.cn.lua", "Version.en.lua", "Version.tw.lua")
    var version = ""
    for (versionFile in listOf) {
        if (version != "") break
        val file = File(addonRoot, versionFile)
        if (!file.exists()) continue
        try {
            val globals = JsePlatform.standardGlobals()

            val chunk = globals.load(
                "${TEMPLATE}${file.readText()}${TEMP_END}",
                "@bigfoot"
            ).call()
            val func = globals[LuaValue.valueOf("Version")]
            version = func.call().toString()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
    return version
}