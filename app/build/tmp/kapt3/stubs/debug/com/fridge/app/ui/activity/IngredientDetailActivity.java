package com.fridge.app.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.fridge.app.R;
import com.fridge.app.data.model.Ingredient;
import com.fridge.app.data.model.IngredientStatus;
import com.fridge.app.data.repository.IngredientRepository;
import com.fridge.app.databinding.ActivityIngredientDetailBinding;
import com.fridge.app.util.ImageUtils;
import com.fridge.app.util.PhotoPicker;
import com.google.android.material.snackbar.Snackbar;
import kotlinx.coroutines.Dispatchers;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 B2\u00020\u0001:\u0001BB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0018H\u0002J\b\u0010\u001a\u001a\u00020\u0018H\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0017\u0010!\u001a\u00020\u001c2\b\u0010\"\u001a\u0004\u0018\u00010\u001cH\u0002\u00a2\u0006\u0002\u0010#J\u0010\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&H\u0002J\u0010\u0010\'\u001a\u00020 2\u0006\u0010%\u001a\u00020&H\u0002J\b\u0010(\u001a\u00020\u0018H\u0002J\u0012\u0010)\u001a\u00020\u00182\b\u0010*\u001a\u0004\u0018\u00010 H\u0002J\u0012\u0010+\u001a\u00020\u00182\b\u0010,\u001a\u0004\u0018\u00010-H\u0014J\u0010\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201H\u0016J\u0010\u00102\u001a\u00020\u00182\u0006\u00103\u001a\u00020 H\u0002J\u0010\u00104\u001a\u00020\u00182\u0006\u00105\u001a\u000206H\u0002J\b\u00107\u001a\u00020\u0018H\u0002J\b\u00108\u001a\u00020\u0018H\u0002J\b\u00109\u001a\u00020\u0018H\u0002J\b\u0010:\u001a\u00020\u0018H\u0002J\b\u0010;\u001a\u00020\u0018H\u0002J\b\u0010<\u001a\u00020\u0018H\u0002J\b\u0010=\u001a\u00020\u0018H\u0002J\u0010\u0010>\u001a\u00020\u00182\u0006\u00103\u001a\u00020\u0016H\u0002J\u0010\u0010?\u001a\u00020\u00182\u0006\u0010@\u001a\u00020 H\u0002J\u0010\u0010A\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\u0010\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u0010\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006C"}, d2 = {"Lcom/fridge/app/ui/activity/IngredientDetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/fridge/app/databinding/ActivityIngredientDetailBinding;", "dateFormat", "Ljava/text/SimpleDateFormat;", "editDateFormat", "editLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "ingredient", "Lcom/fridge/app/data/model/Ingredient;", "ingredientId", "", "photoPicker", "Lcom/fridge/app/util/PhotoPicker;", "photoPickerLauncher", "repository", "Lcom/fridge/app/data/repository/IngredientRepository;", "tempPhotoUri", "Landroid/net/Uri;", "chooseFromGallery", "", "deleteIngredient", "deletePhoto", "getCategoryColor", "", "category", "Lcom/fridge/app/data/model/IngredientCategory;", "getCategoryString", "", "getDaysLeftColor", "days", "(Ljava/lang/Integer;)I", "getStatusColor", "status", "Lcom/fridge/app/data/model/IngredientStatus;", "getStatusString", "loadIngredient", "loadPhoto", "photoUri", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "", "item", "Landroid/view/MenuItem;", "previewImage", "uri", "saveChanges", "dialogBinding", "Lcom/fridge/app/databinding/DialogAddIngredientBinding;", "setupListeners", "setupToolbar", "showDefaultPhoto", "showDeleteConfirmDialog", "showEditDialog", "showPhotoPicker", "takePhoto", "updateIngredientPhoto", "updateTitle", "name", "updateUI", "Companion", "app_debug"})
public final class IngredientDetailActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.fridge.app.databinding.ActivityIngredientDetailBinding binding;
    private com.fridge.app.data.repository.IngredientRepository repository;
    private com.fridge.app.util.PhotoPicker photoPicker;
    private long ingredientId = -1L;
    @org.jetbrains.annotations.Nullable()
    private com.fridge.app.data.model.Ingredient ingredient;
    @org.jetbrains.annotations.Nullable()
    private android.net.Uri tempPhotoUri;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat dateFormat = null;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat editDateFormat = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> photoPickerLauncher = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> editLauncher = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_INGREDIENT_ID = "ingredient_id";
    @org.jetbrains.annotations.NotNull()
    public static final com.fridge.app.ui.activity.IngredientDetailActivity.Companion Companion = null;
    
    public IngredientDetailActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupListeners() {
    }
    
    private final void loadIngredient() {
    }
    
    private final void updateUI(com.fridge.app.data.model.Ingredient ingredient) {
    }
    
    private final void loadPhoto(java.lang.String photoUri) {
    }
    
    private final void showDefaultPhoto() {
    }
    
    private final void showPhotoPicker() {
    }
    
    private final void takePhoto() {
    }
    
    private final void chooseFromGallery() {
    }
    
    private final void updateIngredientPhoto(android.net.Uri uri) {
    }
    
    private final void deletePhoto() {
    }
    
    private final void showEditDialog() {
    }
    
    private final void saveChanges(com.fridge.app.databinding.DialogAddIngredientBinding dialogBinding) {
    }
    
    private final void showDeleteConfirmDialog() {
    }
    
    private final void deleteIngredient() {
    }
    
    private final void previewImage(java.lang.String uri) {
    }
    
    private final void updateTitle(java.lang.String name) {
    }
    
    private final java.lang.String getCategoryString(com.fridge.app.data.model.IngredientCategory category) {
        return null;
    }
    
    private final int getCategoryColor(com.fridge.app.data.model.IngredientCategory category) {
        return 0;
    }
    
    private final java.lang.String getStatusString(com.fridge.app.data.model.IngredientStatus status) {
        return null;
    }
    
    private final int getStatusColor(com.fridge.app.data.model.IngredientStatus status) {
        return 0;
    }
    
    private final int getDaysLeftColor(java.lang.Integer days) {
        return 0;
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/fridge/app/ui/activity/IngredientDetailActivity$Companion;", "", "()V", "EXTRA_INGREDIENT_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}