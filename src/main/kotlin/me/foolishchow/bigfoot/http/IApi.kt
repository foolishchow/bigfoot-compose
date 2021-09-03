package me.foolishchow.bigfoot.http

import com.google.gson.annotations.SerializedName
import me.foolishchow.bigfoot.http.bean.BaseResponse
import me.foolishchow.bigfoot.http.bean.PluginDetail
import me.foolishchow.bigfoot.http.bean.PluginInfo
import retrofit2.Call
import retrofit2.http.GET

import retrofit2.http.Path


private interface IApi {

    @GET("/dl/lists/{type}")
    fun list(
        @Path("type") type: String
    ): Call<BaseResponse<List<PluginInfo>>>

    @GET("/dl/detail/{id}/null")
    fun detail(
        @Path("id") id: String
    ): Call<BaseResponse<PluginDetail>>

}

object Api {
    private val mApi = HttpConfig.BuildRetrofit(IApi::class.java)
    fun list(
        type: String
    ): Call<BaseResponse<List<PluginInfo>>> {
        return mApi.list(type)
    }

    fun detail(id:String): Call<BaseResponse<PluginDetail>> {
        return mApi.detail(id)
    }

}




