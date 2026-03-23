package com.fridge.app.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.FileProvider;
import com.fridge.app.R;
import java.io.File;
import java.util.*;

/**
 * 图片选择器工具类
 * 提供相机拍照和相册选择功能
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ.\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\t2\u0014\u0010\u0010\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0004\u0012\u00020\u00060\u0011J2\u0010\u0012\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\t2\u0018\u0010\u0010\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u0013\u0012\u0004\u0012\u00020\u00060\u0011J\u001e\u0010\u0014\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0002\u0010\u0015\u001a\u00020\u000eJ\u0014\u0010\u0016\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\u001c\u0010\u0017\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0018\u001a\u00020\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/fridge/app/util/PhotoPicker;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "chooseFromGallery", "", "launcher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "createTempPhotoUri", "Landroid/net/Uri;", "handleActivityResult", "resultCode", "", "data", "callback", "Lkotlin/Function1;", "handleMultipleSelectionResult", "", "pickMultipleImages", "maxCount", "pickSingleImage", "takePhoto", "photoUri", "app_debug"})
public final class PhotoPicker {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    public PhotoPicker(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * 从相册选择图片
     */
    public final void chooseFromGallery(@org.jetbrains.annotations.NotNull()
    androidx.activity.result.ActivityResultLauncher<android.content.Intent> launcher) {
    }
    
    /**
     * 拍摄照片
     * @param launcher ActivityResultLauncher
     * @param photoUri 临时文件URI（通过FileProvider创建）
     */
    public final void takePhoto(@org.jetbrains.annotations.NotNull()
    androidx.activity.result.ActivityResultLauncher<android.content.Intent> launcher, @org.jetbrains.annotations.NotNull()
    android.net.Uri photoUri) {
    }
    
    /**
     * 处理Activity返回结果
     * @param resultCode 结果码
     * @param data Intent数据
     * @param callback 回调函数，返回选中的图片URI
     */
    public final void handleActivityResult(int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super android.net.Uri, kotlin.Unit> callback) {
    }
    
    /**
     * 创建临时图片文件URI（用于相机拍照）
     */
    @org.jetbrains.annotations.Nullable()
    public final android.net.Uri createTempPhotoUri() {
        return null;
    }
    
    /**
     * 使用PhotoPicker API (Android 13+)
     * 选择单张图片
     */
    public final void pickSingleImage(@org.jetbrains.annotations.NotNull()
    androidx.activity.result.ActivityResultLauncher<android.content.Intent> launcher) {
    }
    
    /**
     * 使用PhotoPicker API (Android 13+)
     * 选择多张图片
     */
    public final void pickMultipleImages(@org.jetbrains.annotations.NotNull()
    androidx.activity.result.ActivityResultLauncher<android.content.Intent> launcher, int maxCount) {
    }
    
    /**
     * 处理多选返回结果
     */
    public final void handleMultipleSelectionResult(int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<? extends android.net.Uri>, kotlin.Unit> callback) {
    }
}