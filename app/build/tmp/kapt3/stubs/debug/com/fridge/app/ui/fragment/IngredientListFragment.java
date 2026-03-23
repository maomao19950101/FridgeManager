package com.fridge.app.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.fridge.app.R;
import com.fridge.app.data.model.Ingredient;
import com.fridge.app.data.model.IngredientCategory;
import com.fridge.app.databinding.DialogAddIngredientBinding;
import com.fridge.app.databinding.FragmentIngredientListBinding;
import com.fridge.app.ui.adapter.IngredientAdapter;
import com.fridge.app.ui.viewmodel.IngredientViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0017\u001a\u00020\u0015H\u0002J$\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020\u0011H\u0016J\u001a\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u00192\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010#\u001a\u00020\u0011H\u0002J\b\u0010$\u001a\u00020\u0011H\u0002J\b\u0010%\u001a\u00020\u0011H\u0002J\b\u0010&\u001a\u00020\u0011H\u0002J\u0006\u0010\'\u001a\u00020\u0011J\u0010\u0010(\u001a\u00020\u00112\u0006\u0010)\u001a\u00020*H\u0002J\u0010\u0010+\u001a\u00020\u00112\u0006\u0010)\u001a\u00020*H\u0002J\u0010\u0010,\u001a\u00020\u00112\u0006\u0010)\u001a\u00020*H\u0002J\u0012\u0010-\u001a\u00020\u00112\b\u0010)\u001a\u0004\u0018\u00010*H\u0002J\u0016\u0010.\u001a\u00020\u00112\f\u0010/\u001a\b\u0012\u0004\u0012\u00020*00H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\r\u00a8\u00061"}, d2 = {"Lcom/fridge/app/ui/fragment/IngredientListFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/fridge/app/databinding/FragmentIngredientListBinding;", "adapter", "Lcom/fridge/app/ui/adapter/IngredientAdapter;", "binding", "getBinding", "()Lcom/fridge/app/databinding/FragmentIngredientListBinding;", "viewModel", "Lcom/fridge/app/ui/viewmodel/IngredientViewModel;", "getViewModel", "()Lcom/fridge/app/ui/viewmodel/IngredientViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "filterByCategory", "", "category", "Lcom/fridge/app/data/model/IngredientCategory;", "getCategoryDisplayName", "", "getCategoryFromDisplayName", "name", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupFilters", "setupObservers", "setupRecyclerView", "setupSearch", "showAddIngredientDialog", "showDeleteConfirmDialog", "ingredient", "Lcom/fridge/app/data/model/Ingredient;", "showEditIngredientDialog", "showIngredientDetail", "showIngredientDialog", "updateStats", "ingredients", "", "app_debug"})
public final class IngredientListFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.fridge.app.databinding.FragmentIngredientListBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.fridge.app.ui.adapter.IngredientAdapter adapter;
    
    public IngredientListFragment() {
        super();
    }
    
    private final com.fridge.app.databinding.FragmentIngredientListBinding getBinding() {
        return null;
    }
    
    private final com.fridge.app.ui.viewmodel.IngredientViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupObservers() {
    }
    
    private final void setupSearch() {
    }
    
    private final void setupFilters() {
    }
    
    private final void filterByCategory(com.fridge.app.data.model.IngredientCategory category) {
    }
    
    private final void updateStats(java.util.List<com.fridge.app.data.model.Ingredient> ingredients) {
    }
    
    public final void showAddIngredientDialog() {
    }
    
    private final void showEditIngredientDialog(com.fridge.app.data.model.Ingredient ingredient) {
    }
    
    private final void showIngredientDialog(com.fridge.app.data.model.Ingredient ingredient) {
    }
    
    private final void showDeleteConfirmDialog(com.fridge.app.data.model.Ingredient ingredient) {
    }
    
    private final void showIngredientDetail(com.fridge.app.data.model.Ingredient ingredient) {
    }
    
    private final java.lang.String getCategoryDisplayName(com.fridge.app.data.model.IngredientCategory category) {
        return null;
    }
    
    private final com.fridge.app.data.model.IngredientCategory getCategoryFromDisplayName(java.lang.String name) {
        return null;
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}