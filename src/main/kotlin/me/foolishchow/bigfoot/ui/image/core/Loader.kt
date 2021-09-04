package me.foolishchow.bigfoot.ui.image.core

import me.foolishchow.bigfoot.ui.image.model.Task

abstract class Loader(open var task: Task) {
    init {
        fetchImage()
    }

    abstract fun fetchImage(): ByteArray

}