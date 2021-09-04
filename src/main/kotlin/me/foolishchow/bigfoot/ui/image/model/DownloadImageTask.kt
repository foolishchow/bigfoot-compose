package me.foolishchow.bigfoot.ui.image.model

data class DownloadImageTask(override var url: String, var target: String, override var resultCallback: ((result: Result) -> Unit)?) : Task(url, resultCallback)