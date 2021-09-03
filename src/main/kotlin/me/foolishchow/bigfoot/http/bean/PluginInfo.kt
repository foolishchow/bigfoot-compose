package me.foolishchow.bigfoot.http.bean

import com.google.gson.annotations.SerializedName

class PluginInfo {
    @SerializedName("ui_id")
    var id: String = ""

    var name: String = ""

    @SerializedName("descript")
    val description: String = ""

    @SerializedName("update_time")
    val updateTime: String = ""

    val point: String = ""

    val version: String = ""

}