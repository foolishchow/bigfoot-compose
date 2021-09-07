package me.foolishchow.bigfoot.richtext.base

class RichTextImage(
    val src: String = "",
    val width: Int = 0,
    val height: Int = 0
) : RichText {
    override val type: Int
        get() = 2
}