package com.fridge.app.ui.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.fridge.app.R
import com.fridge.app.databinding.ActivityImagePreviewBinding
import com.fridge.app.util.ImageUtils
import kotlinx.coroutines.launch
import java.io.File

/**
 * 图片预览Activity - 全屏图片查看器
 * 支持：缩放、保存、分享、设为壁纸
 */
class ImagePreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImagePreviewBinding
    private var imageUri: String? = null
    private var imageTitle: String? = null
    private var isSystemUiVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 设置全屏
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        binding = ActivityImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // 获取参数
        imageUri = intent.getStringExtra(EXTRA_IMAGE_URI)
        imageTitle = intent.getStringExtra(EXTRA_TITLE)
        
        if (imageUri == null) {
            Toast.makeText(this, R.string.image_load_failed, Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        setupUI()
        loadImage()
        setupListeners()
    }
    
    private fun setupUI() {
        // 设置标题
        binding.tvTitle.text = imageTitle ?: getString(R.string.image_preview)
        
        // 初始显示系统UI
        showSystemUI()
    }
    
    private fun loadImage() {
        binding.progressBar.visibility = View.VISIBLE
        
        val uri = try {
            Uri.parse(imageUri)
        } catch (e: Exception) {
            null
        }
        
        if (uri == null) {
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this, R.string.image_load_failed, Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        Glide.with(this)
            .load(uri)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@ImagePreviewActivity, R.string.image_load_failed, Toast.LENGTH_SHORT).show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }
            })
            .error(R.drawable.ic_food)
            .into(binding.photoView)
    }
    
    private fun setupListeners() {
        // 点击切换系统UI显示/隐藏
        binding.photoView.setOnClickListener {
            toggleSystemUI()
        }
        
        // 返回按钮
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        
        // 分享按钮
        binding.btnShare.setOnClickListener {
            shareImage()
        }
        
        // 保存按钮
        binding.btnSave.setOnClickListener {
            saveImage()
        }
        
        // 更多按钮（旋转等）
        binding.btnMore.setOnClickListener {
            showMoreOptions()
        }
    }
    
    private fun toggleSystemUI() {
        if (isSystemUiVisible) {
            hideSystemUI()
        } else {
            showSystemUI()
        }
    }
    
    private fun hideSystemUI() {
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        
        // 隐藏工具栏
        binding.toolbar.animate()
            .alpha(0f)
            .withEndAction {
                binding.toolbar.visibility = View.GONE
            }
            .setDuration(200)
            .start()
        
        isSystemUiVisible = false
    }
    
    private fun showSystemUI() {
        WindowInsetsControllerCompat(window, binding.root).show(WindowInsetsCompat.Type.systemBars())
        
        // 显示工具栏
        binding.toolbar.visibility = View.VISIBLE
        binding.toolbar.animate()
            .alpha(1f)
            .setDuration(200)
            .start()
        
        isSystemUiVisible = true
    }
    
    private fun shareImage() {
        imageUri?.let { uriStr ->
            try {
                val uri = Uri.parse(uriStr)
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "image/*"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    putExtra(Intent.EXTRA_SUBJECT, imageTitle)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_image)))
            } catch (e: Exception) {
                Toast.makeText(this, R.string.share_failed, Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun saveImage() {
        imageUri?.let { uriStr ->
            lifecycleScope.launch {
                try {
                    val sourceUri = Uri.parse(uriStr)
                    val fileName = "IMG_${System.currentTimeMillis()}.jpg"
                    
                    // 复制到Pictures目录
                    val saved = ImageUtils.copyToPictures(this@ImagePreviewActivity, sourceUri, fileName)
                    
                    if (saved) {
                        Toast.makeText(this@ImagePreviewActivity, R.string.image_saved, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ImagePreviewActivity, R.string.save_failed, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@ImagePreviewActivity, R.string.save_failed, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun showMoreOptions() {
        val options = arrayOf(
            getString(R.string.rotate_left),
            getString(R.string.rotate_right),
            getString(R.string.set_as_wallpaper)
        )
        
        AlertDialog.Builder(this)
            .setTitle(R.string.more_options)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> binding.photoView.rotation = binding.photoView.rotation - 90f
                    1 -> binding.photoView.rotation = binding.photoView.rotation + 90f
                    2 -> setAsWallpaper()
                }
            }
            .show()
    }
    
    private fun setAsWallpaper() {
        imageUri?.let { uriStr ->
            try {
                val uri = Uri.parse(uriStr)
                val wallpaperIntent = Intent(Intent.ACTION_ATTACH_DATA).apply {
                    setDataAndType(uri, "image/*")
                    putExtra("mimeType", "image/*")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                startActivity(Intent.createChooser(wallpaperIntent, getString(R.string.set_as_wallpaper)))
            } catch (e: Exception) {
                Toast.makeText(this, R.string.wallpaper_set_failed, Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && !isSystemUiVisible) {
            hideSystemUI()
        }
    }
    
    companion object {
        const val EXTRA_IMAGE_URI = "image_uri"
        const val EXTRA_TITLE = "title"
    }
}
