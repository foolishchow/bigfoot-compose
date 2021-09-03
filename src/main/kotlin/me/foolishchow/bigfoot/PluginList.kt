package me.foolishchow.bigfoot

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.foolishchow.bigfoot.database.DataBase
import java.awt.Cursor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun drawPluginList() {
    LazyColumn(
        modifier = Modifier.width(280.dp)
            .background(Color(0x330000))
    ) {
        itemsIndexed(DataBase.plugins.value) { index, it ->
            Column(
                modifier =
                Modifier.fillMaxWidth()
                    .background(
                        when {
                            DataBase.selectPlugin.value == it.id -> {
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
                        DataBase.selectPlugin.value = it.id
                        DataBase.getPluginDetail()
                    }
            ) {
                Text(
                    it.name,
                    maxLines = 1,
                    fontSize = 15.sp,
                    color = Theme.PluginTitleColor
                )
                Text(
                    it.description,
                    color = Theme.PluginDescriptionColor,
                    fontSize = 12.sp,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 13.dp)
                )
            }
        }

    }
}
