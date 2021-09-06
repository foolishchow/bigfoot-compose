package me.foolishchow.bigfoot.ui.image.model

import me.foolishchow.bigfoot.ui.image.ImageLoader

open class Task(
    open var url: String,
    open var resultCallback: ((result: Result) -> Unit)?
) {
    companion object {
        fun convertToTask(imageLoader: ImageLoader): Task {
            return if (imageLoader.imgUrl.isNotBlank()) {
                FetchImageTask(imageLoader.imgUrl, imageLoader.resultListener)
            } else {
                DownloadImageTask(imageLoader.downloadUrl, imageLoader.targetDir, imageLoader.resultListener)
            }
        }
    }

}