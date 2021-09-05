package me.foolishchow.bigfoot.database

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import me.foolishchow.bigfoot.http.Api
import me.foolishchow.bigfoot.http.BASE_URL
import me.foolishchow.bigfoot.http.bean.*
import me.foolishchow.bigfoot.http.common.CommonApi
import me.foolishchow.bigfoot.http.common.HtmlPage
import me.foolishchow.bigfoot.nullOrBlankTo
import me.foolishchow.bigfoot.nullTo
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

object DataBase {
    val selectTab = mutableStateOf(1)
    val selectCategory = mutableStateOf("")
    val selectPlugin = mutableStateOf("")
    val selectPluginDetail: MutableState<PluginDetail?> = mutableStateOf(null)
    val plugins = mutableStateOf(mutableListOf<PluginInfo>())
    val totalCategory = mutableStateOf(mutableListOf<CategoryInfo>())

    fun refreshCategory() {
        CommonApi.html("$BASE_URL/dl").enqueue(object : Callback<HtmlPage> {
            override fun onResponse(call: Call<HtmlPage>, response: Response<HtmlPage>) {
                val result = response.body()?.toCategory
                result?.let {
                    totalCategory.value = it.toMutableList()
                    if (selectCategory.value == "") {
                        selectCategory.value = it.first().id
                        getPluginList()
                    }
                }
            }

            override fun onFailure(call: Call<HtmlPage>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getPluginDetail() {
        val id = selectPlugin.value
        Api.detail(id).enqueue(object : Callback<BaseResponse<PluginDetail>> {
            override fun onResponse(
                call: Call<BaseResponse<PluginDetail>>,
                response: Response<BaseResponse<PluginDetail>>
            ) {
                selectPluginDetail.value = response.body()?.result

            }

            override fun onFailure(call: Call<BaseResponse<PluginDetail>>, t: Throwable) {

            }

        })
    }

    fun getPluginList() {
        val value = selectCategory.value
        Api.list(value).enqueue(object : Callback<BaseResponse<List<PluginInfo>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<PluginInfo>>>,
                response: Response<BaseResponse<List<PluginInfo>>>
            ) {
                response.body()?.apply {
                    if (state == 1) {
                        val mutableList = result?.toMutableList() ?: mutableListOf()
                        plugins.value = mutableList
                        if (mutableList.isNotEmpty()) {
                            selectPlugin.value = mutableList.first().id
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<PluginInfo>>>, t: Throwable) {

            }

        })
    }


}


fun parseHtml(content: String?): ContentInfo {
    if (content == null || content.isBlank()) return ContentInfo()
    val doc = Jsoup.parse(content)
    val child = mutableListOf<Dom>()
    doc.childNodes().forEach { element ->
        child.addAll(parseElement(element))
    }
    return ContentInfo().apply {
        list = child
    }
}

private fun parseElement(element: Node): MutableList<Dom> {

    val child = mutableListOf<Dom>()
    if(element !is Element){
        if(element is TextNode){
            val text = element.text() ?: ""
            if(!text.isBlank()){
                child.add(TextDom(text))
            }
        }
        return child
    }

    if (element.children().size == 0) {
        println(element.tagName())
        if (element.tagName().lowercase(Locale.getDefault()) == "img") {
            child.add(
                ImageDom(
                    element.attr("src"),
                    element.attr("width").nullOrBlankTo("0").toInt(),
                    element.attr("height").nullOrBlankTo("0").toInt()
                )
            )
        } else {
            val text = element.html() ?: ""
            if(!text.isBlank()){
                println(element.tagName() +"   "+text)
                child.add(TextDom(text))
            }
        }
    } else {
        element.childNodes().forEach { el ->
            child.addAll(parseElement(el))
        }
    }


    return child
}
