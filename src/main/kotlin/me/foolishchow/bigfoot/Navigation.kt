package me.foolishchow.bigfoot

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerIcon
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.foolishchow.bigfoot.database.DataBase
import me.foolishchow.bigfoot.http.bean.CategoryInfo
import me.foolishchow.bigfoot.http.bean.isSelected
import me.foolishchow.bigfoot.ui.dropdown.DropdownMenu
import me.foolishchow.bigfoot.ui.dropdown.DropdownMenuItem
import me.foolishchow.bigfoot.ui.flow.FlowRow
import java.awt.Cursor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun drawNavigation() {
    val hover = remember { mutableStateOf("") }

    FlowRow(
        mainAxisSpacing = 2.dp,
        crossAxisSpacing = 2.dp,
        modifier = Modifier.fillMaxWidth()
            .background(Theme.NavBackground)
            .padding(2.dp)
    ) {
        DataBase.totalCategory.value.forEach { category ->
            drawCategoryItem(category, hover)
        }
    }
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
private fun drawCategoryItem(
    category: CategoryInfo,
    hover: MutableState<String>
) {
    val selected = category.isSelected(hover, DataBase.selectCategory)

    Box(
        modifier = Modifier
            .pointerIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
            .background(if (selected) Theme.NavBackgroundActive else Color.Transparent)
            .pointerMoveFilter(
                onExit = {
                    hover.value = ""
                    true
                }
            )

    ) {
        Text(
            category.name,
            fontSize = 12.sp,
            color = Theme.NavTextColor,
            modifier = Modifier
                .padding(5.dp)
                .clickable {
                    DataBase.selectCategory.value = category.id
                    DataBase.getPluginList()
                }.pointerMoveFilter(
                    onEnter = {
                        hover.value = category.id
                        true
                    }
                )
        )
        category.child?.let { child ->
            drawDropdownMenu(child, hover, category)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun drawDropdownMenu(
    child: List<CategoryInfo>,
    hover: MutableState<String>,
    category: CategoryInfo
) {
    if (child.isEmpty()) return
    DropdownMenu(
        modifier = Modifier.background(Theme.NavBackgroundActive),
        expanded = hover.value == category.id,
        onDismissRequest = {
            hover.value = ""
        }
    ) {
        child.forEachIndexed { index, pl ->
            val selected = pl.isSelected(DataBase.selectCategory)
            DropdownMenuItem(
                modifier = Modifier
                    .pointerIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
                    .background(if (selected) Theme.NavBackground else Color.Transparent),
                onClick = {
                    DataBase.selectCategory.value = pl.id
                    DataBase.getPluginList()
                    hover.value = pl.id
                }) {
                Text(
                    pl.name,
                    fontSize = 12.sp,
                    color = Theme.NavTextColor
                )
            }
        }
    }
}