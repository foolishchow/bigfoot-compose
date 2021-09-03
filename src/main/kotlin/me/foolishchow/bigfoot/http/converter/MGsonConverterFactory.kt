package me.foolishchow.bigfoot.http.converter

import com.google.gson.Gson
import retrofit2.Converter
import retrofit2.Retrofit
import okhttp3.ResponseBody
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import com.google.gson.GsonBuilder
import me.foolishchow.bigfoot.http.common.HtmlPage
import java.lang.NullPointerException
import java.lang.reflect.Type

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
 */ /**
 * A [converter][Converter.Factory] which uses Gson for JSON.
 *
 *
 * Because Gson is so flexible in the types it supports, this converter assumes that it can handle
 * all types. If you are mixing JSON serialization with something else (such as protocol buffers),
 * you must [add this instance][Retrofit.Builder.addConverterFactory]
 * last to allow the other converters a chance to see their types.
 */
class MGsonConverterFactory private constructor(gson: Gson?) : Converter.Factory() {
    private val gson: Gson
    override fun responseBodyConverter(
        type: Type, annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        if (type.typeName == HtmlPage::class.java.typeName) {
            return PageResponseBodyConverter()
        }
        val adapter = gson.getAdapter(TypeToken.get(type))
        return GsonResponseBodyConverter(adapter)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter(gson, adapter)
    }

    companion object {
        /**
         * Create an instance using a default [Gson] instance for conversion. Encoding to JSON and
         * decoding from JSON (when no charset is specified by a header) will use UTF-8.
         */
        fun create(): MGsonConverterFactory {
            val gson = GsonBuilder()
                .create()
            return create(gson)
        }

        /**
         * Create an instance using `gson` for conversion. Encoding to JSON and
         * decoding from JSON (when no charset is specified by a header) will use UTF-8.
         */
        fun create(gson: Gson?): MGsonConverterFactory {
            return MGsonConverterFactory(gson)
        }
    }

    init {
        if (gson == null) throw NullPointerException("gson == null")
        this.gson = gson
    }
}