package me.foolishchow.bigfoot.http.bean

import androidx.compose.runtime.MutableState
import me.foolishchow.bigfoot.http.common.HtmlPage
import org.jsoup.Jsoup


class CategoryInfo {
    var id: String = ""

    var name: String = ""

    var child: List<CategoryInfo>? = null

    override fun toString(): String {
        return "Cate{ id : $id , name : $name , child : ${child.toString()}}"
    }
}

fun CategoryInfo.isSelected(vararg selects: MutableState<String>): Boolean {
    var selected = false
    selects.forEach {
        if(!selected && isSelected(it)){
            selected = true
        }
    }
    return selected
}
fun CategoryInfo.isSelected(selected: MutableState<String>): Boolean {
    if (selected.value == id) return true
    if (child.isNullOrEmpty()) return false
    var select = false
    child?.forEach { item ->
        if (!select && item.id == selected.value) {
            select = true
        }
    }
    return select
}

val HtmlPage.toCategory: List<CategoryInfo>
    get() {
        val doc = Jsoup.parse(content)
        val result = mutableListOf<CategoryInfo>()
        doc.select("ul.nav > li").forEach { element ->
            val tag = element.select(".tag_first")?.first() ?: return@forEach
            val cate = CategoryInfo()
            cate.id = tag.attr("tagid")
            cate.name = tag.text().trimIndent()

            val nest = element.select(".hide_box a.tag")
            if (nest.size > 1) {
                val children = mutableListOf<CategoryInfo>()
                nest.forEach { c ->
                    val child = CategoryInfo()
                    child.id = c.attr("tagid")
                    child.name = c.text().trimIndent()
                    children.add(child)
                }
                cate.child = children
            }
//            println(cate)
            result.add(cate)
        }
        return result
    }