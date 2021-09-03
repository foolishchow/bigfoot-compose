package me.foolishchow.bigfoot.http.bean

open class BaseResponse<T> {
    var state: Int = 0
    var result: T? = null
}