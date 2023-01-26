package com.vlados_app.myapplication2.common.util

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import java.util.*
import javax.inject.Inject

class DownloadHelper @Inject constructor(
    private val context: Context,
    private val downloadManager: DownloadManager
) {

    companion object {
        private const val TITLE = "Cat is downloaded"
        private const val MIME_TYPE = "image/jpeg"
        private const val fileExtension = ".jpeg"
    }

    fun download(url: String) {
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setAllowedOverRoaming(false)
            .setTitle(TITLE)
            .setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_DOWNLOADS,
                UUID.randomUUID().toString() + fileExtension
            )
            .setMimeType(MIME_TYPE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        downloadManager.enqueue(request)
    }
}