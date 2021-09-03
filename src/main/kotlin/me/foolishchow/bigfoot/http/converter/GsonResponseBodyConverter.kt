package me.foolishchow.bigfoot.http.converter

import com.google.gson.TypeAdapter
import retrofit2.Converter
import okhttp3.ResponseBody
import kotlin.Throws
import java.io.IOException

/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
internal class GsonResponseBodyConverter<T>
    (private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        return try {
            adapter.fromJson(value.charStream())
        } finally {
            value.close()
        }
    }
}