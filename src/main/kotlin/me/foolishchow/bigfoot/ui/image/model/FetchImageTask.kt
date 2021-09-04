package me.foolishchow.bigfoot.ui.image.model

data class FetchImageTask(override var url: String, override var resultCallback: ((result: Result) -> Unit)?) : Task(url,resultCallback)