package com.fridge.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\r\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r\u00a8\u0006\u000e"}, d2 = {"Lcom/fridge/app/data/model/IngredientCategory;", "", "(Ljava/lang/String;I)V", "VEGETABLE", "FRUIT", "MEAT", "SEAFOOD", "EGG", "DAIRY", "GRAIN", "SEASONING", "CANNED", "FROZEN", "OTHER", "app_debug"})
public enum IngredientCategory {
    /*public static final*/ VEGETABLE /* = new VEGETABLE() */,
    /*public static final*/ FRUIT /* = new FRUIT() */,
    /*public static final*/ MEAT /* = new MEAT() */,
    /*public static final*/ SEAFOOD /* = new SEAFOOD() */,
    /*public static final*/ EGG /* = new EGG() */,
    /*public static final*/ DAIRY /* = new DAIRY() */,
    /*public static final*/ GRAIN /* = new GRAIN() */,
    /*public static final*/ SEASONING /* = new SEASONING() */,
    /*public static final*/ CANNED /* = new CANNED() */,
    /*public static final*/ FROZEN /* = new FROZEN() */,
    /*public static final*/ OTHER /* = new OTHER() */;
    
    IngredientCategory() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.fridge.app.data.model.IngredientCategory> getEntries() {
        return null;
    }
}