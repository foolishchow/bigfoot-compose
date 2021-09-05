package me.foolishchow.bigfoot

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.foolishchow.bigfoot.database.DataBase
import me.foolishchow.bigfoot.http.bean.ImageDom
import me.foolishchow.bigfoot.http.bean.TextDom
import me.foolishchow.bigfoot.ui.flow.FlowRow
import me.foolishchow.bigfoot.ui.image.NetworkImage

@Composable
fun drawDetail() {
    val detail = DataBase.selectPluginDetail.value ?: return
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val stateVertical = rememberScrollState(0)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(stateVertical)
        ) {


            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth().height(72.dp)) {
                    Box(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Image(
                            painter = BitmapPainter(useResource("header.png", ::loadImageBitmap)),
                            contentDescription = "",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.height(35.dp)
                        )
                        Text(
                            detail.name,
                            fontWeight = FontWeight.W600,
                            fontSize = 18.sp,
                            color = Theme.PluginTitleColor,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                Row {
                    DataBase.selectPluginDetail.value?.let { detail ->
                        NetworkImage(
                            url = "http://wowui.178.com/data/wow/${DataBase.selectPlugin.value}/ui_logo.jpg",
                            modifier = Modifier.width(200.dp).height(140.dp).background(Color(0x22000000))
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "更新： ${detail.updateTime}",
                                color = Theme.DetailColor, fontSize = 12.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                "作者： ${detail.author}",
                                color = Theme.DetailColor, fontSize = 12.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                "下载： ${detail.download}",
                                color = Theme.DetailColor, fontSize = 12.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "大小： ${detail.file.size}Kb",
                                color = Theme.DetailColor, fontSize = 12.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                "来源： ${detail.sourceName}",
                                color = Theme.DetailColor, fontSize = 12.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                "评分： ${detail.point}",
                                color = Theme.DetailColor, fontSize = 12.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }


                }
                Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                FlowRow(
                    modifier = Modifier.background(Color(0x1D000000)).fillMaxWidth().padding(3.dp),
                    mainAxisSpacing = 3.dp,
                    crossAxisSpacing = 3.dp
                ) {
                    Box(
                        modifier = Modifier
                            .cursorPoint()
                            .background(if (DataBase.selectTab.value == 1) Theme.NavBackgroundActive else Color.Transparent)
                            .padding(horizontal = 10.dp, vertical = 7.dp)
                            .clickable {
                                DataBase.selectTab.value = 1
                            }
                    ) {
                        Text("功能说明", color = Theme.DetailColor)
                    }
                    Box(
                        modifier = Modifier.cursorPoint()
                            .background(if (DataBase.selectTab.value == 2) Theme.NavBackgroundActive else Color.Transparent)
                            .padding(horizontal = 10.dp, vertical = 7.dp)
                            .clickable {
                                DataBase.selectTab.value = 2
                            }
                    ) {
                        Text("更新日志", color = Theme.DetailColor)
                    }
                    Box(
                        modifier = Modifier.cursorPoint()
                            .background(if (DataBase.selectTab.value == 3) Theme.NavBackgroundActive else Color.Transparent)
                            .padding(horizontal = 10.dp, vertical = 7.dp)
                            .clickable {
                                DataBase.selectTab.value = 3
                            }
                    ) {
                        Text("安装说明", color = Theme.DetailColor)
                    }
                }

                Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                val info = when (DataBase.selectTab.value) {
                    1 -> detail.info1
                    2 -> detail.info2
                    else -> detail.info3
                }
                info.list.forEach {
                    if (it is TextDom) {
                        Text(it.content, color = Theme.DetailContent)
                    } else if (it is ImageDom) {
                        NetworkImage(
                            url = it.src,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.width(it.width.dp).height(it.height.dp).background(Color(0x33000000))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp).fillMaxWidth())
            }
        }
        VerticalScrollbar(
            modifier = Modifier
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(stateVertical)
        )

    }


}