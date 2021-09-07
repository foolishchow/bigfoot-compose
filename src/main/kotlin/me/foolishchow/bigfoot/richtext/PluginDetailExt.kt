package me.foolishchow.bigfoot.richtext

import me.foolishchow.bigfoot.fragments.nullOrBlankTo
import me.foolishchow.bigfoot.richtext.base.RichText
import me.foolishchow.bigfoot.richtext.base.RichTextImage
import me.foolishchow.bigfoot.richtext.base.RichTextText
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import java.util.*


fun parseHtml(content: String?): RichTextInfo {
    if (content == null || content.isBlank()) return RichTextInfo()
    val doc = Jsoup.parse(content)
    val child = mutableListOf<RichText>()
    doc.childNodes().forEach { element ->
        child.addAll(parseElement(element))
    }
    return RichTextInfo().apply {
        list = child
    }
}

private fun parseElement(element: Node): MutableList<RichText> {

    val child = mutableListOf<RichText>()
    if(element !is Element){
        if(element is TextNode){
            val text = element.text() ?: ""
            if(text.isNotBlank()){
                child.add(RichTextText(text))
            }
        }
        return child
    }

    if (element.childNodes().size == 0) {
        if (element.tagName().lowercase(Locale.getDefault()) == "img") {
            child.add(
                RichTextImage(
                    element.attr("src"),
                    element.attr("width").nullOrBlankTo("0").toInt(),
                    element.attr("height").nullOrBlankTo("0").toInt()
                )
            )
        } else {
            val text = element.html() ?: ""
            if(text.isNotBlank()){
                child.add(RichTextText(text))
            }
        }
    } else {
        element.childNodes().forEach { el ->
            child.addAll(parseElement(el))
        }
    }


    return child
}