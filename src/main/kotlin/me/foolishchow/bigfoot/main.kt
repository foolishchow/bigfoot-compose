package me.foolishchow.bigfoot

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerIcon
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.*
import me.foolishchow.bigfoot.http.Api
import me.foolishchow.bigfoot.http.BASE_URL
import me.foolishchow.bigfoot.http.bean.BaseResponse
import me.foolishchow.bigfoot.http.bean.CategoryInfo
import me.foolishchow.bigfoot.http.bean.PluginInfo
import me.foolishchow.bigfoot.http.bean.toCategory
import me.foolishchow.bigfoot.http.common.CommonApi
import me.foolishchow.bigfoot.http.common.HtmlPage
import me.foolishchow.bigfoot.ui.flow.FlowRow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.awt.Cursor


object DB {
    val selectCategory = mutableStateOf("")
    val selectPlugin = mutableStateOf("")
    val plugins = mutableStateOf(mutableListOf<PluginInfo>())
    val totalCategory = mutableStateOf(mutableListOf<CategoryInfo>())

    fun refreshCategory() {
        CommonApi.html("$BASE_URL/dl").enqueue(object : Callback<HtmlPage> {
            override fun onResponse(call: Call<HtmlPage>, response: Response<HtmlPage>) {
                val result = response.body()?.toCategory
                result?.let {
                    totalCategory.value = it.toMutableList()
                    if (selectCategory.value == "") {
                        selectCategory.value = it.first().id
                        getPluginList()
                    }
                }
            }

            override fun onFailure(call: Call<HtmlPage>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getPluginList() {
        val value = selectCategory.value
        Api.list(value).enqueue(object : Callback<BaseResponse<List<PluginInfo>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<PluginInfo>>>,
                response: Response<BaseResponse<List<PluginInfo>>>
            ) {
                response.body()?.apply {
                    if (state == 1) {
                        val mutableList = result?.toMutableList() ?: mutableListOf()
                        plugins.value = mutableList
                        if (mutableList.isNotEmpty()) {
                            selectPlugin.value = mutableList.first().id
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<PluginInfo>>>, t: Throwable) {

            }

        })
    }



}


//val HtmlPage.category
fun main() {
    DB.refreshCategory()
    application {
        val windowState = rememberWindowState()
        windowState.size = WindowSize(823.dp, 615.dp)
        windowState.placement = WindowPlacement.Floating
        windowState.position = WindowPosition.Aligned(Alignment.Center)
        Window(
            onCloseRequest = ::exitApplication,
            title = "Big Foot",
            state = windowState,
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = BitmapPainter(useResource("bg.jpg", ::loadImageBitmap)),
                contentDescription = ""
            )

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.fillMaxWidth().height(6.dp))
                drawTopSearch()
                Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                drawNavigation()
                Row {
                    drawLeft()
                }
            }
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun drawLeft() {
    LazyColumn(
        modifier = Modifier.width(280.dp)
            .background(Color(0x330000))
    ) {
        itemsIndexed(DB.plugins.value) { index, it ->
            Column(
                modifier =
                Modifier.fillMaxWidth()
                    .background(
                        when {
                            DB.selectPlugin.value == it.id -> {
                                Color(0xff3e2111)
                            }
                            index % 2 == 1 -> {
                                Color(0xff1f100b)
                            }
                            else -> {
                                Color.Transparent
                            }
                        }
                    )
                    .padding(14.dp)
                    .pointerIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
                    .clickable {
                        DB.selectPlugin.value = it.id
                    }
            ) {
                Text(
                    it.name,
                    maxLines = 1,
                    fontSize = 15.sp,
                    color = Color(0xffa99877)
                )
                Text(
                    it.description,
                    color = Color(0xff756e6a),
                    fontSize = 12.sp,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 13.dp)
                )
            }
        }

    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun drawNavigation() {
    val hover = remember { mutableStateOf("") }

    FlowRow(
        mainAxisSpacing = 2.dp,
        crossAxisSpacing = 2.dp,
        modifier = Modifier.fillMaxWidth()
            .background(Color(0XFF1d0f0a))
            .padding(2.dp)
    ) {
        DB.totalCategory.value.forEach { category ->
            Box(
                modifier = Modifier
                    .pointerIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
                    .background(when{
                        DB.selectCategory.value == category.id->{
                            Color(0xff3e1f14)
                        }
                        hover.value == category.id->{
                            Color(0xff3e1f14)
                        }
                        else->{
                            Color.Transparent
                        }
                    })
                    .padding(5.dp)
                    .pointerMoveFilter(
                        onEnter = {
                            hover.value = category.id
                            true
                        },
                        onExit = {
                            hover.value = ""
                            true
                        }
                    )
                    .clickable {
                        DB.selectCategory.value = category.id
                        DB.getPluginList()
                    }
            ){
                Text(
                    category.name,
                    color = Color(0xff877d77)
                    )
                category.child?.let { child->
                    if(child.isEmpty()) return@let
                    DropdownMenu(
                        expanded = hover.value == category.id,
                        onDismissRequest = {
                            hover.value = ""
                        }
                    ){
                        child.forEachIndexed{index,pl->
                            DropdownMenuItem(
                                contentPadding = PaddingValues.Absolute(),
                                onClick = {
                                DB.selectCategory.value = category.id
                                DB.getPluginList()
                                hover.value = pl.id
                            }){
                                Text(
                                    pl.name,
                                    color = Color(0xff877d77)
                                )
                            }
                        }
                    }
                }

            }

        }
    }
}

@Composable
fun drawTopSearch() {
    Box(modifier = Modifier.fillMaxWidth().height(38.dp))
}
