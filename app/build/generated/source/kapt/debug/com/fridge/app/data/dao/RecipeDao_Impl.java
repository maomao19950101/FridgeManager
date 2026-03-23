package com.fridge.app.data.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.fridge.app.data.local.Converters;
import com.fridge.app.data.model.Difficulty;
import com.fridge.app.data.model.Recipe;
import com.fridge.app.data.model.RecipeIngredient;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RecipeDao_Impl implements RecipeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Recipe> __insertionAdapterOfRecipe;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Recipe> __deletionAdapterOfRecipe;

  private final EntityDeletionOrUpdateAdapter<Recipe> __updateAdapterOfRecipe;

  private final SharedSQLiteStatement __preparedStmtOfDeleteRecipeById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllRecipes;

  public RecipeDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecipe = new EntityInsertionAdapter<Recipe>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `recipes` (`id`,`name`,`description`,`imageUrl`,`cookingTime`,`difficulty`,`servings`,`ingredients`,`steps`,`tags`,`cuisineType`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Recipe value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getImageUrl());
        }
        stmt.bindLong(5, value.getCookingTime());
        final String _tmp = __converters.fromDifficulty(value.getDifficulty());
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp);
        }
        stmt.bindLong(7, value.getServings());
        final String _tmp_1 = __converters.fromRecipeIngredientList(value.getIngredients());
        if (_tmp_1 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_1);
        }
        final String _tmp_2 = __converters.fromStringList(value.getSteps());
        if (_tmp_2 == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, _tmp_2);
        }
        final String _tmp_3 = __converters.fromStringList(value.getTags());
        if (_tmp_3 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, _tmp_3);
        }
        if (value.getCuisineType() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getCuisineType());
        }
      }
    };
    this.__deletionAdapterOfRecipe = new EntityDeletionOrUpdateAdapter<Recipe>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `recipes` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Recipe value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfRecipe = new EntityDeletionOrUpdateAdapter<Recipe>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `recipes` SET `id` = ?,`name` = ?,`description` = ?,`imageUrl` = ?,`cookingTime` = ?,`difficulty` = ?,`servings` = ?,`ingredients` = ?,`steps` = ?,`tags` = ?,`cuisineType` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Recipe value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getImageUrl());
        }
        stmt.bindLong(5, value.getCookingTime());
        final String _tmp = __converters.fromDifficulty(value.getDifficulty());
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp);
        }
        stmt.bindLong(7, value.getServings());
        final String _tmp_1 = __converters.fromRecipeIngredientList(value.getIngredients());
        if (_tmp_1 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_1);
        }
        final String _tmp_2 = __converters.fromStringList(value.getSteps());
        if (_tmp_2 == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, _tmp_2);
        }
        final String _tmp_3 = __converters.fromStringList(value.getTags());
        if (_tmp_3 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, _tmp_3);
        }
        if (value.getCuisineType() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getCuisineType());
        }
        stmt.bindLong(12, value.getId());
      }
    };
    this.__preparedStmtOfDeleteRecipeById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM recipes WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllRecipes = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM recipes";
        return _query;
      }
    };
  }

  @Override
  public long insertRecipe(final Recipe recipe) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfRecipe.insertAndReturnId(recipe);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertRecipes(final List<Recipe> recipes) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfRecipe.insert(recipes);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteRecipe(final Recipe recipe) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfRecipe.handle(recipe);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateRecipe(final Recipe recipe) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfRecipe.handle(recipe);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteRecipeById(final long id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteRecipeById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteRecipeById.release(_stmt);
    }
  }

  @Override
  public void deleteAllRecipes() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllRecipes.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllRecipes.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Recipe>> getAllRecipes() {
    final String _sql = "SELECT * FROM recipes ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"recipes"}, false, new Callable<List<Recipe>>() {
      @Override
      public List<Recipe> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfCookingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTime");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCuisineType = CursorUtil.getColumnIndexOrThrow(_cursor, "cuisineType");
          final List<Recipe> _result = new ArrayList<Recipe>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Recipe _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final int _tmpCookingTime;
            _tmpCookingTime = _cursor.getInt(_cursorIndexOfCookingTime);
            final Difficulty _tmpDifficulty;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDifficulty);
            }
            _tmpDifficulty = __converters.toDifficulty(_tmp);
            final int _tmpServings;
            _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            final List<RecipeIngredient> _tmpIngredients;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __converters.toRecipeIngredientList(_tmp_1);
            final List<String> _tmpSteps;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSteps)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSteps);
            }
            _tmpSteps = __converters.toStringList(_tmp_2);
            final List<String> _tmpTags;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfTags);
            }
            _tmpTags = __converters.toStringList(_tmp_3);
            final String _tmpCuisineType;
            if (_cursor.isNull(_cursorIndexOfCuisineType)) {
              _tmpCuisineType = null;
            } else {
              _tmpCuisineType = _cursor.getString(_cursorIndexOfCuisineType);
            }
            _item = new Recipe(_tmpId,_tmpName,_tmpDescription,_tmpImageUrl,_tmpCookingTime,_tmpDifficulty,_tmpServings,_tmpIngredients,_tmpSteps,_tmpTags,_tmpCuisineType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Recipe getRecipeById(final long id) {
    final String _sql = "SELECT * FROM recipes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
      final int _cursorIndexOfCookingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTime");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
      final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
      final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
      final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
      final int _cursorIndexOfCuisineType = CursorUtil.getColumnIndexOrThrow(_cursor, "cuisineType");
      final Recipe _result;
      if(_cursor.moveToFirst()) {
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        final String _tmpImageUrl;
        if (_cursor.isNull(_cursorIndexOfImageUrl)) {
          _tmpImageUrl = null;
        } else {
          _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
        }
        final int _tmpCookingTime;
        _tmpCookingTime = _cursor.getInt(_cursorIndexOfCookingTime);
        final Difficulty _tmpDifficulty;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfDifficulty)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfDifficulty);
        }
        _tmpDifficulty = __converters.toDifficulty(_tmp);
        final int _tmpServings;
        _tmpServings = _cursor.getInt(_cursorIndexOfServings);
        final List<RecipeIngredient> _tmpIngredients;
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfIngredients)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
        }
        _tmpIngredients = __converters.toRecipeIngredientList(_tmp_1);
        final List<String> _tmpSteps;
        final String _tmp_2;
        if (_cursor.isNull(_cursorIndexOfSteps)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getString(_cursorIndexOfSteps);
        }
        _tmpSteps = __converters.toStringList(_tmp_2);
        final List<String> _tmpTags;
        final String _tmp_3;
        if (_cursor.isNull(_cursorIndexOfTags)) {
          _tmp_3 = null;
        } else {
          _tmp_3 = _cursor.getString(_cursorIndexOfTags);
        }
        _tmpTags = __converters.toStringList(_tmp_3);
        final String _tmpCuisineType;
        if (_cursor.isNull(_cursorIndexOfCuisineType)) {
          _tmpCuisineType = null;
        } else {
          _tmpCuisineType = _cursor.getString(_cursorIndexOfCuisineType);
        }
        _result = new Recipe(_tmpId,_tmpName,_tmpDescription,_tmpImageUrl,_tmpCookingTime,_tmpDifficulty,_tmpServings,_tmpIngredients,_tmpSteps,_tmpTags,_tmpCuisineType);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Recipe>> getRecipesByDifficulty(final Difficulty difficulty) {
    final String _sql = "SELECT * FROM recipes WHERE difficulty = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromDifficulty(difficulty);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"recipes"}, false, new Callable<List<Recipe>>() {
      @Override
      public List<Recipe> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfCookingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTime");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCuisineType = CursorUtil.getColumnIndexOrThrow(_cursor, "cuisineType");
          final List<Recipe> _result = new ArrayList<Recipe>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Recipe _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final int _tmpCookingTime;
            _tmpCookingTime = _cursor.getInt(_cursorIndexOfCookingTime);
            final Difficulty _tmpDifficulty;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfDifficulty);
            }
            _tmpDifficulty = __converters.toDifficulty(_tmp_1);
            final int _tmpServings;
            _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            final List<RecipeIngredient> _tmpIngredients;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __converters.toRecipeIngredientList(_tmp_2);
            final List<String> _tmpSteps;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfSteps)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfSteps);
            }
            _tmpSteps = __converters.toStringList(_tmp_3);
            final List<String> _tmpTags;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfTags);
            }
            _tmpTags = __converters.toStringList(_tmp_4);
            final String _tmpCuisineType;
            if (_cursor.isNull(_cursorIndexOfCuisineType)) {
              _tmpCuisineType = null;
            } else {
              _tmpCuisineType = _cursor.getString(_cursorIndexOfCuisineType);
            }
            _item = new Recipe(_tmpId,_tmpName,_tmpDescription,_tmpImageUrl,_tmpCookingTime,_tmpDifficulty,_tmpServings,_tmpIngredients,_tmpSteps,_tmpTags,_tmpCuisineType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Recipe>> getRecipesByMaxTime(final int maxTime) {
    final String _sql = "SELECT * FROM recipes WHERE cookingTime <= ? ORDER BY cookingTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, maxTime);
    return __db.getInvalidationTracker().createLiveData(new String[]{"recipes"}, false, new Callable<List<Recipe>>() {
      @Override
      public List<Recipe> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfCookingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTime");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCuisineType = CursorUtil.getColumnIndexOrThrow(_cursor, "cuisineType");
          final List<Recipe> _result = new ArrayList<Recipe>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Recipe _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final int _tmpCookingTime;
            _tmpCookingTime = _cursor.getInt(_cursorIndexOfCookingTime);
            final Difficulty _tmpDifficulty;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDifficulty);
            }
            _tmpDifficulty = __converters.toDifficulty(_tmp);
            final int _tmpServings;
            _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            final List<RecipeIngredient> _tmpIngredients;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __converters.toRecipeIngredientList(_tmp_1);
            final List<String> _tmpSteps;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSteps)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSteps);
            }
            _tmpSteps = __converters.toStringList(_tmp_2);
            final List<String> _tmpTags;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfTags);
            }
            _tmpTags = __converters.toStringList(_tmp_3);
            final String _tmpCuisineType;
            if (_cursor.isNull(_cursorIndexOfCuisineType)) {
              _tmpCuisineType = null;
            } else {
              _tmpCuisineType = _cursor.getString(_cursorIndexOfCuisineType);
            }
            _item = new Recipe(_tmpId,_tmpName,_tmpDescription,_tmpImageUrl,_tmpCookingTime,_tmpDifficulty,_tmpServings,_tmpIngredients,_tmpSteps,_tmpTags,_tmpCuisineType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Recipe>> getRecipesByCuisine(final String cuisineType) {
    final String _sql = "SELECT * FROM recipes WHERE cuisineType = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (cuisineType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, cuisineType);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"recipes"}, false, new Callable<List<Recipe>>() {
      @Override
      public List<Recipe> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfCookingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTime");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCuisineType = CursorUtil.getColumnIndexOrThrow(_cursor, "cuisineType");
          final List<Recipe> _result = new ArrayList<Recipe>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Recipe _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final int _tmpCookingTime;
            _tmpCookingTime = _cursor.getInt(_cursorIndexOfCookingTime);
            final Difficulty _tmpDifficulty;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDifficulty);
            }
            _tmpDifficulty = __converters.toDifficulty(_tmp);
            final int _tmpServings;
            _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            final List<RecipeIngredient> _tmpIngredients;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __converters.toRecipeIngredientList(_tmp_1);
            final List<String> _tmpSteps;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSteps)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSteps);
            }
            _tmpSteps = __converters.toStringList(_tmp_2);
            final List<String> _tmpTags;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfTags);
            }
            _tmpTags = __converters.toStringList(_tmp_3);
            final String _tmpCuisineType;
            if (_cursor.isNull(_cursorIndexOfCuisineType)) {
              _tmpCuisineType = null;
            } else {
              _tmpCuisineType = _cursor.getString(_cursorIndexOfCuisineType);
            }
            _item = new Recipe(_tmpId,_tmpName,_tmpDescription,_tmpImageUrl,_tmpCookingTime,_tmpDifficulty,_tmpServings,_tmpIngredients,_tmpSteps,_tmpTags,_tmpCuisineType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Recipe>> searchRecipes(final String query) {
    final String _sql = "SELECT * FROM recipes WHERE name LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%' ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"recipes"}, false, new Callable<List<Recipe>>() {
      @Override
      public List<Recipe> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfCookingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTime");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCuisineType = CursorUtil.getColumnIndexOrThrow(_cursor, "cuisineType");
          final List<Recipe> _result = new ArrayList<Recipe>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Recipe _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final int _tmpCookingTime;
            _tmpCookingTime = _cursor.getInt(_cursorIndexOfCookingTime);
            final Difficulty _tmpDifficulty;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDifficulty);
            }
            _tmpDifficulty = __converters.toDifficulty(_tmp);
            final int _tmpServings;
            _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            final List<RecipeIngredient> _tmpIngredients;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __converters.toRecipeIngredientList(_tmp_1);
            final List<String> _tmpSteps;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSteps)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSteps);
            }
            _tmpSteps = __converters.toStringList(_tmp_2);
            final List<String> _tmpTags;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfTags);
            }
            _tmpTags = __converters.toStringList(_tmp_3);
            final String _tmpCuisineType;
            if (_cursor.isNull(_cursorIndexOfCuisineType)) {
              _tmpCuisineType = null;
            } else {
              _tmpCuisineType = _cursor.getString(_cursorIndexOfCuisineType);
            }
            _item = new Recipe(_tmpId,_tmpName,_tmpDescription,_tmpImageUrl,_tmpCookingTime,_tmpDifficulty,_tmpServings,_tmpIngredients,_tmpSteps,_tmpTags,_tmpCuisineType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Recipe>> getRecipesByTag(final String tag) {
    final String _sql = "SELECT * FROM recipes WHERE ? IN (tags) ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tag == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tag);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"recipes"}, false, new Callable<List<Recipe>>() {
      @Override
      public List<Recipe> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfCookingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTime");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCuisineType = CursorUtil.getColumnIndexOrThrow(_cursor, "cuisineType");
          final List<Recipe> _result = new ArrayList<Recipe>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Recipe _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final int _tmpCookingTime;
            _tmpCookingTime = _cursor.getInt(_cursorIndexOfCookingTime);
            final Difficulty _tmpDifficulty;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDifficulty);
            }
            _tmpDifficulty = __converters.toDifficulty(_tmp);
            final int _tmpServings;
            _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            final List<RecipeIngredient> _tmpIngredients;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __converters.toRecipeIngredientList(_tmp_1);
            final List<String> _tmpSteps;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSteps)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSteps);
            }
            _tmpSteps = __converters.toStringList(_tmp_2);
            final List<String> _tmpTags;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfTags);
            }
            _tmpTags = __converters.toStringList(_tmp_3);
            final String _tmpCuisineType;
            if (_cursor.isNull(_cursorIndexOfCuisineType)) {
              _tmpCuisineType = null;
            } else {
              _tmpCuisineType = _cursor.getString(_cursorIndexOfCuisineType);
            }
            _item = new Recipe(_tmpId,_tmpName,_tmpDescription,_tmpImageUrl,_tmpCookingTime,_tmpDifficulty,_tmpServings,_tmpIngredients,_tmpSteps,_tmpTags,_tmpCuisineType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public int getRecipeCount() {
    final String _sql = "SELECT COUNT(*) FROM recipes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
