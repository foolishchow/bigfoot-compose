package me.foolishchow.bigfoot

import androidx.compose.animation.scaleIn
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.*
import me.foolishchow.bigfoot.database.DataBase
import me.foolishchow.bigfoot.ui.image.NetworkImage


fun main() {
    DataBase.refreshCategory()
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
                    drawPluginList()
                    Box(modifier = Modifier.weight(1f)) {
                        drawDetail()
                    }
                }
            }
        }
    }

}

@Composable
fun drawDetail() {
    val detail = DataBase.selectPluginDetail.value ?: return
    Column(
        modifier = Modifier.fillMaxWidth()
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
            NetworkImage(
                url = "http://wowui.178.com/data/wow/${DataBase.selectPlugin.value}/ui_logo.jpg",
                modifier = Modifier.width(200.dp).height(140.dp)
            )
            DataBase.selectPluginDetail.value?.let { detail->
                println(detail.contentDescription)
            }

        }
    }
}




@Composable
fun drawTopSearch() {
    Box(modifier = Modifier.fillMaxWidth().height(38.dp))
}
