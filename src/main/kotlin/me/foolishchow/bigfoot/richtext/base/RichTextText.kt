package me.foolishchow.bigfoot.richtext.base

class RichTextText(
    var content: String
) : RichText {
    override val type: Int
        get() = 1
}