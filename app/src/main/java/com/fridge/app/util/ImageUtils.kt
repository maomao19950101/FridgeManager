package com.fridge.app.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * 图片处理工具类
 * 提供图片压缩、旋转、保存等功能
 */
object ImageUtils {

    private const val MAX_IMAGE_SIZE = 1024 // 最大图片尺寸（像素）
    private const val COMPRESS_QUALITY = 85 // 压缩质量
    private const val MAX_FILE_SIZE = 2 * 1024 * 1024 // 最大文件大小 2MB

    /**
     * 创建图片文件
     * @return 创建的图片文件
     */
    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            ?: context.filesDir
        
        // 确保目录存在
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
        
        return File(storageDir, "JPEG_${timeStamp}.jpg")
    }

    /**
     * 处理图片（压缩、旋转）
     * @param context 上下文
     * @param sourceUri 源图片URI
     * @param maxSize 最大尺寸
     * @param quality 压缩质量
     * @return 处理后的文件
     */
    suspend fun processImage(
        context: Context,
        sourceUri: Uri,
        maxSize: Int = MAX_IMAGE_SIZE,
        quality: Int = COMPRESS_QUALITY
    ): File? = withContext(Dispatchers.IO) {
        try {
            // 读取图片尺寸
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            
            context.contentResolver.openInputStream(sourceUri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream, null, options)
            }

            // 计算缩放比例
            val scaleFactor = calculateScaleFactor(options.outWidth, options.outHeight, maxSize)

            // 读取并缩放图片
            val bitmapOptions = BitmapFactory.Options().apply {
                inSampleSize = scaleFactor
                inJustDecodeBounds = false
            }

            var bitmap = context.contentResolver.openInputStream(sourceUri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream, null, bitmapOptions)
            } ?: return@withContext null

            // 旋转图片
            val rotation = getImageRotation(context, sourceUri)
            if (rotation != 0) {
                bitmap = rotateBitmap(bitmap, rotation)
            }

            // 压缩并保存
            val outputFile = createImageFile(context)
            FileOutputStream(outputFile).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            }

            // 如果文件仍然太大，进一步压缩
            var compressedFile = outputFile
            while (compressedFile.length() > MAX_FILE_SIZE && quality > 50) {
                val newQuality = quality - 10
                compressedFile = compressImage(context, outputFile, newQuality)
            }

            bitmap.recycle()
            compressedFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 压缩图片
     */
    private fun compressImage(context: Context, file: File, quality: Int): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        val outputFile = createImageFile(context)
        
        FileOutputStream(outputFile).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        }
        
        bitmap.recycle()
        
        // 删除原文件
        if (file.exists() && file != outputFile) {
            file.delete()
        }
        
        return outputFile
    }

    /**
     * 计算缩放比例
     */
    private fun calculateScaleFactor(width: Int, height: Int, maxSize: Int): Int {
        var scale = 1
        while (width / scale > maxSize || height / scale > maxSize) {
            scale *= 2
        }
        return scale
    }

    /**
     * 获取图片旋转角度
     */
    fun getImageRotation(context: Context, uri: Uri): Int {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.use { stream ->
                val exif = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ExifInterface(stream)
                } else {
                    // 对于旧版本，需要先保存到临时文件
                    val tempFile = File(context.cacheDir, "temp_exif.jpg")
                    stream.copyTo(FileOutputStream(tempFile))
                    ExifInterface(tempFile.path)
                }
                
                when (exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> 270
                    else -> 0
                }
            } ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    /**
     * 旋转Bitmap
     */
    fun rotateBitmap(bitmap: Bitmap, degrees: Int): Bitmap {
        if (degrees == 0) return bitmap
        
        val matrix = Matrix().apply {
            postRotate(degrees.toFloat())
        }
        
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    /**
     * 将图片复制到Pictures公共目录
     */
    suspend fun copyToPictures(
        context: Context,
        sourceUri: Uri,
        fileName: String
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/FridgeManager")
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
            }

            val uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            ) ?: return@withContext false

            context.contentResolver.openInputStream(sourceUri)?.use { input ->
                context.contentResolver.openOutputStream(uri)?.use { output ->
                    input.copyTo(output)
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.clear()
                values.put(MediaStore.Images.Media.IS_PENDING, 0)
                context.contentResolver.update(uri, values, null, null)
            }

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 删除图片
     */
    fun deleteImage(context: Context, uriString: String): Boolean {
        return try {
            val uri = Uri.parse(uriString)
            val rowsDeleted = context.contentResolver.delete(uri, null, null)
            rowsDeleted > 0
        } catch (e: Exception) {
            // 可能是应用私有目录的文件
            try {
                val file = File(Uri.parse(uriString).path ?: return false)
                if (file.exists()) {
                    file.delete()
                } else {
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    /**
     * 获取图片尺寸
     */
    fun getImageDimensions(context: Context, uri: Uri): Pair<Int, Int>? {
        return try {
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream, null, options)
            }
            Pair(options.outWidth, options.outHeight)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 生成缩略图
     */
    suspend fun createThumbnail(
        context: Context,
        sourceUri: Uri,
        maxSize: Int = 256
    ): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            context.contentResolver.openInputStream(sourceUri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream, null, options)
            }

            val scaleFactor = calculateScaleFactor(options.outWidth, options.outHeight, maxSize)
            
            val bitmapOptions = BitmapFactory.Options().apply {
                inSampleSize = scaleFactor
            }

            context.contentResolver.openInputStream(sourceUri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream, null, bitmapOptions)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 获取文件大小（可读格式）
     */
    fun getFileSizeString(sizeInBytes: Long): String {
        return when {
            sizeInBytes < 1024 -> "$sizeInBytes B"
            sizeInBytes < 1024 * 1024 -> "${sizeInBytes / 1024} KB"
            else -> String.format("%.2f MB", sizeInBytes / (1024.0 * 1024.0))
        }
    }

    /**
     * 清理缓存图片
     */
    fun clearCache(context: Context) {
        try {
            val cacheDir = context.cacheDir
            cacheDir.listFiles()?.forEach { file ->
                if (file.isFile && file.name.startsWith("temp_")) {
                    file.delete()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
