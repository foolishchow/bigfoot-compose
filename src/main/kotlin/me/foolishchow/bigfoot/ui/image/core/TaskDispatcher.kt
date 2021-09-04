package me.foolishchow.bigfoot.ui.image.core

import androidx.compose.ui.graphics.asImageBitmap
import me.foolishchow.bigfoot.ui.image.ImageLoader
import me.foolishchow.bigfoot.ui.image.model.Result
import me.foolishchow.bigfoot.ui.image.model.Status
import org.jetbrains.skija.Image
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TaskDispatcher : Thread() {
    private var taskQueue = TaskQueue.instance
    private var cacheStrategy = ImageLoader.config.cacheStrategy
    private val executor: ExecutorService = Executors.newFixedThreadPool(10)
    override fun run() {
        while (!interrupted()) {
            val task = taskQueue.take()
            executor.run {
                if (cacheStrategy.contains(task.url) && cacheStrategy.get(task.url) != null) {
                    val imageBytes = cacheStrategy.get(task.url)!!
                    task.resultCallback?.let {
                        it(
                            Result(
                                Status.SUCCESS,
                                "load from cache",
                                Image.makeFromEncoded(imageBytes).asImageBitmap()
                            )
                        )
                    }
                } else {
                    val imageBytes = SweetLoader(task).fetchImage()
                    if (imageBytes.isNotEmpty()) {
                        task.resultCallback?.let {
                            it(
                                Result(
                                    Status.SUCCESS,
                                    "load from network",
                                    Image.makeFromEncoded(imageBytes).asImageBitmap()
                                )
                            )
                        }
                        // cache
                        cacheStrategy.store(task.url, imageBytes)
                    } else {
                        task.resultCallback?.let {
                            it(
                                Result(
                                    Status.FAILED,
                                    "load from network failed",
                                    Image.makeFromEncoded(imageBytes).asImageBitmap()
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}