package me.foolishchow.bigfoot.http

import me.foolishchow.bigfoot.http.converter.MGsonConverterFactory
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


private const val DEFAULT_CONNECT_TIMEOUT: Long = 10
private const val DEFAULT_READ_TIMEOUT: Long = 5
private const val DEFAULT_MAX_IDLE_CONNECTION = 10
private const val DEFAULT_KEEP_ALIVE_DURATION = 60
const val BASE_URL = "http://wowbf.178.com"
object HttpConfig {
    fun <T> BuildRetrofit(service: Class<T>): T {
        val client = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS) //设置超时时间
            .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
            .connectionPool(
                ConnectionPool(
                    DEFAULT_MAX_IDLE_CONNECTION,
                    DEFAULT_KEEP_ALIVE_DURATION.toLong(),
                    TimeUnit.SECONDS
                )
            )
            .retryOnConnectionFailure(false)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client.build())
            .addConverterFactory(MGsonConverterFactory.create())
            .build()

        return retrofit.create<T>(service)
    }
}