package me.foolishchow.bigfoot.http.common

import me.foolishchow.bigfoot.http.HttpConfig
import retrofit2.Call
import retrofit2.http.GET

import retrofit2.http.Url

class HtmlPage(var content: String)

private interface ICommonApi {

    @GET
    fun html(
        @Url url: String
    ): Call<HtmlPage>

}

object CommonApi {
    private val mApi = HttpConfig.BuildRetrofit(ICommonApi::class.java)

    fun html(url: String): Call<HtmlPage> {
        return mApi.html(url)
    }

}

