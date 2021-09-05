package me.foolishchow.bigfoot

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.*
import me.foolishchow.bigfoot.database.DataBase


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
                        Box(modifier = Modifier.weight(1f).fillMaxHeight()){
                            drawDetail()
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
