package me.foolishchow.bigfoot.http.bean

import com.google.gson.annotations.SerializedName
import me.foolishchow.bigfoot.richtext.RichTextInfo
import me.foolishchow.bigfoot.richtext.parseHtml


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

    val info1: RichTextInfo by lazy {
        parseHtml(contentDescription)
    }

    val info2: RichTextInfo by lazy {
        parseHtml(updateLog)
    }

    val info3: RichTextInfo by lazy {
        parseHtml(installDescription)
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

class FileInfo {
    @SerializedName("file_id")
    val fileId: String = ""

    @SerializedName("file_name")
    val fileName: String = ""

    @SerializedName("size")
    val size: String = ""
}





