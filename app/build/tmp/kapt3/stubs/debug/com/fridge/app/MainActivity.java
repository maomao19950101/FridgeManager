package com.fridge.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.fridge.app.ui.adapter.ViewPagerAdapter;
import com.fridge.app.ui.fragment.IngredientListFragment;
import com.fridge.app.ui.fragment.RecipeListFragment;
import com.fridge.app.ui.fragment.StatisticsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\u0012\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0014J\b\u0010\u000e\u001a\u00020\nH\u0002J\b\u0010\u000f\u001a\u00020\nH\u0002J\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/fridge/app/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "fabAdd", "Lcom/google/android/material/floatingactionbutton/FloatingActionButton;", "tabLayout", "Lcom/google/android/material/tabs/TabLayout;", "viewPager", "Landroidx/viewpager2/widget/ViewPager2;", "initViews", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupFab", "setupViewPager", "updateFabForPage", "position", "", "Companion", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.viewpager2.widget.ViewPager2 viewPager;
    private com.google.android.material.tabs.TabLayout tabLayout;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fabAdd;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_TAB_INDEX = "tab_index";
    @org.jetbrains.annotations.NotNull()
    public static final com.fridge.app.MainActivity.Companion Companion = null;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initViews() {
    }
    
    private final void setupViewPager() {
    }
    
    private final void setupFab() {
    }
    
    private final void updateFabForPage(int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/fridge/app/MainActivity$Companion;", "", "()V", "EXTRA_TAB_INDEX", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}