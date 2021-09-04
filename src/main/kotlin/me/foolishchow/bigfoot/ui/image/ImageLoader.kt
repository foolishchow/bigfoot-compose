package me.foolishchow.bigfoot.ui.image

import androidx.compose.foundation.Image
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import me.foolishchow.bigfoot.ui.image.core.TaskDispatcher
import me.foolishchow.bigfoot.ui.image.core.TaskQueue
import me.foolishchow.bigfoot.ui.image.model.Config
import me.foolishchow.bigfoot.ui.image.model.Result
import me.foolishchow.bigfoot.ui.image.model.Status
import me.foolishchow.bigfoot.ui.image.model.Task

class ImageLoader {
    var imgUrl: String = ""
    var downloadUrl: String = ""
    var targetDir: String = ""
    var resultListener: ((Result) -> Unit)? = null
    init {
        TaskDispatcher().start()
        println("TaskDispatcher().start()")
//        println(config.diskCacheTarget)
    }

    companion object {
        val config: Config by lazy { Config() }
        val instance: ImageLoader by lazy { ImageLoader() }
    }


    fun loadUrl(url: String): ImageLoader {
        this.imgUrl = url
        this.downloadUrl = ""
        this.targetDir = ""
        return this
    }

    fun download(url: String, target: String): ImageLoader {
        this.downloadUrl = url
        this.targetDir = targetDir
        this.imgUrl = ""
        return this
    }

    fun timeout(second: Int): ImageLoader {
        config.timeout = second * 1000
        return this
    }

    fun enableDiskCache(enable: Boolean): ImageLoader {
        config.enableDiskCache = enable
        return this
    }

    fun diskCacheTarget(target: String): ImageLoader {
        config.diskCacheTarget = target
        return this
    }

    fun listen(resultListener: (result: Result) -> Unit): ImageLoader {
        this.resultListener = resultListener
        return this
    }

    fun exec(): ImageLoader {
        TaskQueue.instance.add(Task.convertToTask(this))
        return this
    }
}


@Composable
fun NetworkImage(
    url: String, modifier: Modifier,
    contentScale: ContentScale = ContentScale.Inside
) {
    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
    imageBitmap.value?.let {
        Image(
            painter = BitmapPainter(it),
            contentDescription = "",
            modifier = modifier,
            contentScale = contentScale
        )
    }
    LaunchedEffect(url) {
        ImageLoader.instance
            .loadUrl(url)
            .listen {
                if (it.status == Status.SUCCESS) {
                    imageBitmap.value = it.content
                } else {
                    println("图片加载失败")
                }
            }
            .exec()

    }

}