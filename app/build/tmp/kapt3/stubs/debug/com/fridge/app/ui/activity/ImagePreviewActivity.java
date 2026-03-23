package com.fridge.app.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fridge.app.R;
import com.fridge.app.databinding.ActivityImagePreviewBinding;
import com.fridge.app.util.ImageUtils;
import java.io.File;

/**
 * 图片预览Activity - 全屏图片查看器
 * 支持：缩放、保存、分享、设为壁纸
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\u000bH\u0002J\u0012\u0010\r\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014J\u0010\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\tH\u0016J\b\u0010\u0012\u001a\u00020\u000bH\u0002J\b\u0010\u0013\u001a\u00020\u000bH\u0002J\b\u0010\u0014\u001a\u00020\u000bH\u0002J\b\u0010\u0015\u001a\u00020\u000bH\u0002J\b\u0010\u0016\u001a\u00020\u000bH\u0002J\b\u0010\u0017\u001a\u00020\u000bH\u0002J\b\u0010\u0018\u001a\u00020\u000bH\u0002J\b\u0010\u0019\u001a\u00020\u000bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/fridge/app/ui/activity/ImagePreviewActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/fridge/app/databinding/ActivityImagePreviewBinding;", "imageTitle", "", "imageUri", "isSystemUiVisible", "", "hideSystemUI", "", "loadImage", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onWindowFocusChanged", "hasFocus", "saveImage", "setAsWallpaper", "setupListeners", "setupUI", "shareImage", "showMoreOptions", "showSystemUI", "toggleSystemUI", "Companion", "app_debug"})
public final class ImagePreviewActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.fridge.app.databinding.ActivityImagePreviewBinding binding;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String imageUri;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String imageTitle;
    private boolean isSystemUiVisible = true;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_IMAGE_URI = "image_uri";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_TITLE = "title";
    @org.jetbrains.annotations.NotNull()
    public static final com.fridge.app.ui.activity.ImagePreviewActivity.Companion Companion = null;
    
    public ImagePreviewActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupUI() {
    }
    
    private final void loadImage() {
    }
    
    private final void setupListeners() {
    }
    
    private final void toggleSystemUI() {
    }
    
    private final void hideSystemUI() {
    }
    
    private final void showSystemUI() {
    }
    
    private final void shareImage() {
    }
    
    private final void saveImage() {
    }
    
    private final void showMoreOptions() {
    }
    
    private final void setAsWallpaper() {
    }
    
    @java.lang.Override()
    public void onWindowFocusChanged(boolean hasFocus) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/fridge/app/ui/activity/ImagePreviewActivity$Companion;", "", "()V", "EXTRA_IMAGE_URI", "", "EXTRA_TITLE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}