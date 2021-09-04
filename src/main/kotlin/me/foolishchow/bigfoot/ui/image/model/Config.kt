package me.foolishchow.bigfoot.ui.image.model

import me.foolishchow.bigfoot.ui.image.cache.Cache
import me.foolishchow.bigfoot.ui.image.cache.DefaultCache
import retrofit2.http.GET

data class Config(
    var timeout: Int = 5000,
    var enableDiskCache: Boolean = true,
    var diskCacheTarget: String = System.getProperty("java.io.tmpdir"),
    //= "/Users/wangke/Downloads/ComposeImageCache",
    var cacheStrategy: Cache<String, ByteArray> = DefaultCache()
)