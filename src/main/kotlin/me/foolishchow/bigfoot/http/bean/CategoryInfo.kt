package me.foolishchow.bigfoot.http.bean

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