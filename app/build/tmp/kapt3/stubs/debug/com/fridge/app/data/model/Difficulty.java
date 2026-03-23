package com.fridge.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.fridge.app.data.local.Converters;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/fridge/app/data/model/Difficulty;", "", "(Ljava/lang/String;I)V", "EASY", "MEDIUM", "HARD", "app_debug"})
public enum Difficulty {
    /*public static final*/ EASY /* = new EASY() */,
    /*public static final*/ MEDIUM /* = new MEDIUM() */,
    /*public static final*/ HARD /* = new HARD() */;
    
    Difficulty() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.fridge.app.data.model.Difficulty> getEntries() {
        return null;
    }
}