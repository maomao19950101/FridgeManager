package com.fridge.app.util

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import java.io.File
import java.util.*

/**
 * 图片选择器工具类
 * 提供相机拍照和相册选择功能
 */
class PhotoPicker(private val context: Context) {

    /**
     * 从相册选择图片
     */
    fun chooseFromGallery(launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png", "image/webp"))
        }
        launcher.launch(Intent.createChooser(intent, context.getString(R.string.choose_photo)))
    }

    /**
     * 拍摄照片
     * @param launcher ActivityResultLauncher
     * @param photoUri 临时文件URI（通过FileProvider创建）
     */
    fun takePhoto(launcher: ActivityResultLauncher<Intent>, photoUri: Uri) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
        launcher.launch(intent)
    }

    /**
     * 处理Activity返回结果
     * @param resultCode 结果码
     * @param data Intent数据
     * @param callback 回调函数，返回选中的图片URI
     */
    fun handleActivityResult(
        resultCode: Int,
        data: Intent?,
        callback: (Uri?) -> Unit
    ) {
        if (resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            callback(uri)
        } else {
            callback(null)
        }
    }

    /**
     * 创建临时图片文件URI（用于相机拍照）
     */
    fun createTempPhotoUri(): Uri? {
        return try {
            val photoFile = ImageUtils.createImageFile(context)
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                photoFile
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 使用PhotoPicker API (Android 13+)
     * 选择单张图片
     */
    fun pickSingleImage(launcher: ActivityResultLauncher<Intent>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ 使用 PhotoPicker
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES).apply {
                type = "image/*"
            }
            launcher.launch(intent)
        } else {
            // 旧版本使用传统方式
            chooseFromGallery(launcher)
        }
    }

    /**
     * 使用PhotoPicker API (Android 13+)
     * 选择多张图片
     */
    fun pickMultipleImages(launcher: ActivityResultLauncher<Intent>, maxCount: Int = 5) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES).apply {
                type = "image/*"
                putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, maxCount)
            }
            launcher.launch(intent)
        } else {
            // 旧版本使用传统方式
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            launcher.launch(Intent.createChooser(intent, context.getString(R.string.choose_photos)))
        }
    }

    /**
     * 处理多选返回结果
     */
    fun handleMultipleSelectionResult(
        resultCode: Int,
        data: Intent?,
        callback: (List<Uri>) -> Unit
    ) {
        if (resultCode != Activity.RESULT_OK) {
            callback(emptyList())
            return
        }

        val uris = mutableListOf<Uri>()
        
        data?.let { intent ->
            // 单选
            intent.data?.let {
                uris.add(it)
            }
            
            // 多选（Android 12及以下）
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                intent.clipData?.let { clipData ->
                    for (i in 0 until clipData.itemCount) {
                        clipData.getItemAt(i).uri?.let { uri ->
                            uris.add(uri)
                        }
                    }
                }
            }
        }
        
        callback(uris)
    }
}
