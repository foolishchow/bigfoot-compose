package me.foolishchow.bigfoot.exts

import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.io.FilenameFilter

fun Frame.selectFile(
    title: String = "选择文件",
    filter: FilenameFilter? = null
): File? {
    val dialog = FileDialog(this, title).apply {
        isMultipleMode = false
        filenameFilter = filter
        isVisible = true
    }
    return if (dialog.files.isNotEmpty()) {
        dialog.files[0]
    } else {
        null
    }
}