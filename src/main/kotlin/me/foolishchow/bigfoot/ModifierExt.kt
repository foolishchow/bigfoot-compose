package me.foolishchow.bigfoot

import androidx.annotation.IntDef
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerIcon
import java.awt.Cursor


@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.cursorPoint(): Modifier {
    return this.pointerIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
}

fun <T> T?.nullTo(value:T):T{
    return this ?: value
}

fun String.nullOrBlankTo(value: String): String {
    return this.ifBlank { value }
}