package com.fridge.app.util;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import kotlinx.coroutines.Dispatchers;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 图片处理工具类
 * 提供图片压缩、旋转、保存等功能
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ \u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0004H\u0002J&\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eJ*\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\n\u001a\u00020\u0004H\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u0018J\u000e\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\"J$\u0010#\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010$2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020\u0016J\u0016\u0010&\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020\u0016J4\u0010\'\u001a\u0004\u0018\u00010\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\n\u001a\u00020\u00042\b\b\u0002\u0010\u0012\u001a\u00020\u0004H\u0086@\u00a2\u0006\u0002\u0010(J\u0016\u0010)\u001a\u00020\u001c2\u0006\u0010*\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/fridge/app/util/ImageUtils;", "", "()V", "COMPRESS_QUALITY", "", "MAX_FILE_SIZE", "MAX_IMAGE_SIZE", "calculateScaleFactor", "width", "height", "maxSize", "clearCache", "", "context", "Landroid/content/Context;", "compressImage", "Ljava/io/File;", "file", "quality", "copyToPictures", "", "sourceUri", "Landroid/net/Uri;", "fileName", "", "(Landroid/content/Context;Landroid/net/Uri;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createImageFile", "createThumbnail", "Landroid/graphics/Bitmap;", "(Landroid/content/Context;Landroid/net/Uri;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteImage", "uriString", "getFileSizeString", "sizeInBytes", "", "getImageDimensions", "Lkotlin/Pair;", "uri", "getImageRotation", "processImage", "(Landroid/content/Context;Landroid/net/Uri;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "rotateBitmap", "bitmap", "degrees", "app_debug"})
public final class ImageUtils {
    private static final int MAX_IMAGE_SIZE = 1024;
    private static final int COMPRESS_QUALITY = 85;
    private static final int MAX_FILE_SIZE = 2097152;
    @org.jetbrains.annotations.NotNull()
    public static final com.fridge.app.util.ImageUtils INSTANCE = null;
    
    private ImageUtils() {
        super();
    }
    
    /**
     * 创建图片文件
     * @return 创建的图片文件
     */
    @org.jetbrains.annotations.NotNull()
    public final java.io.File createImageFile(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    /**
     * 处理图片（压缩、旋转）
     * @param context 上下文
     * @param sourceUri 源图片URI
     * @param maxSize 最大尺寸
     * @param quality 压缩质量
     * @return 处理后的文件
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object processImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri sourceUri, int maxSize, int quality, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.io.File> $completion) {
        return null;
    }
    
    /**
     * 压缩图片
     */
    private final java.io.File compressImage(android.content.Context context, java.io.File file, int quality) {
        return null;
    }
    
    /**
     * 计算缩放比例
     */
    private final int calculateScaleFactor(int width, int height, int maxSize) {
        return 0;
    }
    
    /**
     * 获取图片旋转角度
     */
    public final int getImageRotation(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return 0;
    }
    
    /**
     * 旋转Bitmap
     */
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap rotateBitmap(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap bitmap, int degrees) {
        return null;
    }
    
    /**
     * 将图片复制到Pictures公共目录
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object copyToPictures(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri sourceUri, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * 删除图片
     */
    public final boolean deleteImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String uriString) {
        return false;
    }
    
    /**
     * 获取图片尺寸
     */
    @org.jetbrains.annotations.Nullable()
    public final kotlin.Pair<java.lang.Integer, java.lang.Integer> getImageDimensions(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return null;
    }
    
    /**
     * 生成缩略图
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createThumbnail(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri sourceUri, int maxSize, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super android.graphics.Bitmap> $completion) {
        return null;
    }
    
    /**
     * 获取文件大小（可读格式）
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFileSizeString(long sizeInBytes) {
        return null;
    }
    
    /**
     * 清理缓存图片
     */
    public final void clearCache(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
}