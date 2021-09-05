package me.foolishchow.bigfoot.http.bean

import com.google.gson.annotations.SerializedName
import me.foolishchow.bigfoot.database.parseHtml


/**
"ui_id": "1818",
"name": "Ez ChatBar",
"create_time": "1598078160",
"update_time": "2021-09-03",
"download": "18,844",
"p1": "0",
"p2": "0",
"p3": "0",
"p4": "0",
"p5": "0",
"recommend": "0",
"good": "20",
"bad": "5",
"point": "63.4",
"pointed": "0",
"key": null,
"off_url": "https:\/\/www.curseforge.com\/wow\/addons\/ezchatbar",
"source_url": "https:\/\/www.curseforge.com\/wow\/addons\/ezchatbar",
"author": "dygdyg",
"bbs": null,
"descript": "Addon for to simplify the use of the standard chat.",
"info1": null,
"info2": null,
"info3": null,
"tag_id": "1001",
"status": "1",
"language": "\u4e2d\u6587",
"source_update_time": null,
"bit": "1",
"game_version": "46",
"source_name": "curseforge",
"souuce_descript": "Addon for to simplify the use of the standard chat.",
"edit": "0",
"source_id": "403805",
"favorite": 0,
"file": {
"file_id": "22222",
"file_name": "EzChatBar-release-0.32.47.zip",
"size": "1,228.04"
}
 */
class PluginDetail {
    @SerializedName("ui_id")
    var id: String = ""

    var name: String = ""

    @SerializedName("descript")
    val description: String = ""

    @SerializedName("update_time")
    val updateTime: String = ""

    val point: String = ""

    val version: String = ""

    val author: String = ""

    @SerializedName("off_url")
    val officeUrl: String = ""

    @SerializedName("source_url")
    val sourceUrl: String = ""

    @SerializedName("info1")
    val contentDescription: String = ""

    var _info1:ContentInfo? = null
    val info1:ContentInfo
        get() {
            if(_info1 == null){
                _info1 = parseHtml(contentDescription)
            }
            return _info1!!
        }

    var _info2:ContentInfo? = null
    val info2:ContentInfo
        get() {
            if(_info2 == null){
                _info2 = parseHtml(updateLog)
            }
            return _info2!!
        }

    var _info3:ContentInfo? = null
    val info3:ContentInfo
        get() {
            if(_info3 == null){
                _info3 = parseHtml(installDescription)
            }
            return _info3!!
        }
    @SerializedName("info2")
    val updateLog: String = ""

    @SerializedName("info3")
    val installDescription: String = ""

    val download: String = ""

    @SerializedName("source_name")
    val sourceName: String = ""
    val file: FileInfo = FileInfo()
}

interface Dom {
    val type: Int
}

class TextDom(
    var content: String
) : Dom {
    override val type: Int
        get() = 1
}

class ImageDom(
    val src: String = "",
    val width: Int = 0,
    val height: Int = 0
) : Dom {
    override val type: Int
        get() = 2
}

class ContentInfo {
    var list = listOf<Dom>()
}

class FileInfo {
    @SerializedName("file_id")
    val fileId: String = ""

    @SerializedName("file_name")
    val fileName: String = ""

    @SerializedName("size")
    val size: String = ""
}