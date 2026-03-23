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
import com.fridge.app.data.model.Ingredient;
import com.fridge.app.data.model.IngredientCategory;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class IngredientDao_Impl implements IngredientDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Ingredient> __insertionAdapterOfIngredient;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Ingredient> __deletionAdapterOfIngredient;

  private final EntityDeletionOrUpdateAdapter<Ingredient> __updateAdapterOfIngredient;

  private final SharedSQLiteStatement __preparedStmtOfDeleteIngredientById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllIngredients;

  public IngredientDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfIngredient = new EntityInsertionAdapter<Ingredient>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `ingredients` (`id`,`name`,`category`,`quantity`,`unit`,`addDate`,`expireDate`,`photoUri`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Ingredient value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        final String _tmp = __converters.fromCategory(value.getCategory());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        stmt.bindDouble(4, value.getQuantity());
        if (value.getUnit() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getUnit());
        }
        final Long _tmp_1 = __converters.dateToTimestamp(value.getAddDate());
        if (_tmp_1 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, _tmp_1);
        }
        final Long _tmp_2 = __converters.dateToTimestamp(value.getExpireDate());
        if (_tmp_2 == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, _tmp_2);
        }
        if (value.getPhotoUri() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getPhotoUri());
        }
        if (value.getNotes() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getNotes());
        }
      }
    };
    this.__deletionAdapterOfIngredient = new EntityDeletionOrUpdateAdapter<Ingredient>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ingredients` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Ingredient value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfIngredient = new EntityDeletionOrUpdateAdapter<Ingredient>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `ingredients` SET `id` = ?,`name` = ?,`category` = ?,`quantity` = ?,`unit` = ?,`addDate` = ?,`expireDate` = ?,`photoUri` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Ingredient value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        final String _tmp = __converters.fromCategory(value.getCategory());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        stmt.bindDouble(4, value.getQuantity());
        if (value.getUnit() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getUnit());
        }
        final Long _tmp_1 = __converters.dateToTimestamp(value.getAddDate());
        if (_tmp_1 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, _tmp_1);
        }
        final Long _tmp_2 = __converters.dateToTimestamp(value.getExpireDate());
        if (_tmp_2 == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, _tmp_2);
        }
        if (value.getPhotoUri() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getPhotoUri());
        }
        if (value.getNotes() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getNotes());
        }
        stmt.bindLong(10, value.getId());
      }
    };
    this.__preparedStmtOfDeleteIngredientById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM ingredients WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllIngredients = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM ingredients";
        return _query;
      }
    };
  }

  @Override
  public long insertIngredient(final Ingredient ingredient) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfIngredient.insertAndReturnId(ingredient);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertIngredients(final List<Ingredient> ingredients) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfIngredient.insert(ingredients);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteIngredient(final Ingredient ingredient) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfIngredient.handle(ingredient);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateIngredient(final Ingredient ingredient) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfIngredient.handle(ingredient);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteIngredientById(final long id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteIngredientById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteIngredientById.release(_stmt);
    }
  }

  @Override
  public void deleteAllIngredients() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllIngredients.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllIngredients.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Ingredient>> getAllIngredients() {
    final String _sql = "SELECT * FROM ingredients ORDER BY expireDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"ingredients"}, false, new Callable<List<Ingredient>>() {
      @Override
      public List<Ingredient> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfAddDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addDate");
          final int _cursorIndexOfExpireDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expireDate");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Ingredient> _result = new ArrayList<Ingredient>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Ingredient _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final IngredientCategory _tmpCategory;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.toCategory(_tmp);
            final float _tmpQuantity;
            _tmpQuantity = _cursor.getFloat(_cursorIndexOfQuantity);
            final String _tmpUnit;
            if (_cursor.isNull(_cursorIndexOfUnit)) {
              _tmpUnit = null;
            } else {
              _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            }
            final Date _tmpAddDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfAddDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfAddDate);
            }
            _tmpAddDate = __converters.fromTimestamp(_tmp_1);
            final Date _tmpExpireDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfExpireDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfExpireDate);
            }
            _tmpExpireDate = __converters.fromTimestamp(_tmp_2);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new Ingredient(_tmpId,_tmpName,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpAddDate,_tmpExpireDate,_tmpPhotoUri,_tmpNotes);
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
  public List<CategoryCount> getCategoryCounts() {
    final String _sql = "SELECT category, COUNT(*) as count FROM ingredients GROUP BY category";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCategory = 0;
      final int _cursorIndexOfCount = 1;
      final List<CategoryCount> _result = new ArrayList<CategoryCount>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CategoryCount _item;
        final IngredientCategory _tmpCategory;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfCategory);
        }
        _tmpCategory = __converters.toCategory(_tmp);
        final int _tmpCount;
        _tmpCount = _cursor.getInt(_cursorIndexOfCount);
        _item = new CategoryCount(_tmpCategory,_tmpCount);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getCountByCategory(final IngredientCategory category) {
    final String _sql = "SELECT COUNT(*) FROM ingredients WHERE category = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromCategory(category);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
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

  @Override
  public List<Ingredient> getAllIngredientsSync() {
    final String _sql = "SELECT * FROM ingredients";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
      final int _cursorIndexOfAddDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addDate");
      final int _cursorIndexOfExpireDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expireDate");
      final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final List<Ingredient> _result = new ArrayList<Ingredient>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Ingredient _item;
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final IngredientCategory _tmpCategory;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfCategory);
        }
        _tmpCategory = __converters.toCategory(_tmp);
        final float _tmpQuantity;
        _tmpQuantity = _cursor.getFloat(_cursorIndexOfQuantity);
        final String _tmpUnit;
        if (_cursor.isNull(_cursorIndexOfUnit)) {
          _tmpUnit = null;
        } else {
          _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
        }
        final Date _tmpAddDate;
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfAddDate)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfAddDate);
        }
        _tmpAddDate = __converters.fromTimestamp(_tmp_1);
        final Date _tmpExpireDate;
        final Long _tmp_2;
        if (_cursor.isNull(_cursorIndexOfExpireDate)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getLong(_cursorIndexOfExpireDate);
        }
        _tmpExpireDate = __converters.fromTimestamp(_tmp_2);
        final String _tmpPhotoUri;
        if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
          _tmpPhotoUri = null;
        } else {
          _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
        }
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item = new Ingredient(_tmpId,_tmpName,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpAddDate,_tmpExpireDate,_tmpPhotoUri,_tmpNotes);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getExpiredCount(final Date currentDate) {
    final String _sql = "SELECT COUNT(*) FROM ingredients WHERE expireDate IS NOT NULL AND expireDate < ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(currentDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
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

  @Override
  public int getExpiringCountBetween(final Date startDate, final Date endDate) {
    final String _sql = "SELECT COUNT(*) FROM ingredients WHERE expireDate IS NOT NULL AND expireDate >= ? AND expireDate <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = __converters.dateToTimestamp(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
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

  @Override
  public List<MonthlyCount> getMonthlyAddedCounts(final Date startDate) {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            strftime('%Y-%m', addDate / 1000, 'unixepoch', 'localtime') as month,\n"
            + "            COUNT(*) as count\n"
            + "        FROM ingredients\n"
            + "        WHERE addDate >= ?\n"
            + "        GROUP BY month\n"
            + "        ORDER BY month ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMonth = 0;
      final int _cursorIndexOfCount = 1;
      final List<MonthlyCount> _result = new ArrayList<MonthlyCount>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MonthlyCount _item;
        final String _tmpMonth;
        if (_cursor.isNull(_cursorIndexOfMonth)) {
          _tmpMonth = null;
        } else {
          _tmpMonth = _cursor.getString(_cursorIndexOfMonth);
        }
        final int _tmpCount;
        _tmpCount = _cursor.getInt(_cursorIndexOfCount);
        _item = new MonthlyCount(_tmpMonth,_tmpCount);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<MonthlyCount> getMonthlyExpiredCounts(final Date startDate, final Date currentDate) {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            strftime('%Y-%m', expireDate / 1000, 'unixepoch', 'localtime') as month,\n"
            + "            COUNT(*) as count\n"
            + "        FROM ingredients\n"
            + "        WHERE expireDate IS NOT NULL AND expireDate < ? AND expireDate >= ?\n"
            + "        GROUP BY month\n"
            + "        ORDER BY month ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(currentDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = __converters.dateToTimestamp(startDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMonth = 0;
      final int _cursorIndexOfCount = 1;
      final List<MonthlyCount> _result = new ArrayList<MonthlyCount>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MonthlyCount _item;
        final String _tmpMonth;
        if (_cursor.isNull(_cursorIndexOfMonth)) {
          _tmpMonth = null;
        } else {
          _tmpMonth = _cursor.getString(_cursorIndexOfMonth);
        }
        final int _tmpCount;
        _tmpCount = _cursor.getInt(_cursorIndexOfCount);
        _item = new MonthlyCount(_tmpMonth,_tmpCount);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Date getEarliestAddDate() {
    final String _sql = "SELECT MIN(addDate) FROM ingredients";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Date _result;
      if(_cursor.moveToFirst()) {
        final Long _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(0);
        }
        _result = __converters.fromTimestamp(_tmp);
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
  public List<Ingredient> getIngredientsAddedBetween(final Date startDate, final Date endDate) {
    final String _sql = "SELECT * FROM ingredients WHERE addDate >= ? AND addDate <= ? ORDER BY addDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = __converters.dateToTimestamp(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
      final int _cursorIndexOfAddDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addDate");
      final int _cursorIndexOfExpireDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expireDate");
      final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final List<Ingredient> _result = new ArrayList<Ingredient>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Ingredient _item;
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final IngredientCategory _tmpCategory;
        final String _tmp_2;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getString(_cursorIndexOfCategory);
        }
        _tmpCategory = __converters.toCategory(_tmp_2);
        final float _tmpQuantity;
        _tmpQuantity = _cursor.getFloat(_cursorIndexOfQuantity);
        final String _tmpUnit;
        if (_cursor.isNull(_cursorIndexOfUnit)) {
          _tmpUnit = null;
        } else {
          _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
        }
        final Date _tmpAddDate;
        final Long _tmp_3;
        if (_cursor.isNull(_cursorIndexOfAddDate)) {
          _tmp_3 = null;
        } else {
          _tmp_3 = _cursor.getLong(_cursorIndexOfAddDate);
        }
        _tmpAddDate = __converters.fromTimestamp(_tmp_3);
        final Date _tmpExpireDate;
        final Long _tmp_4;
        if (_cursor.isNull(_cursorIndexOfExpireDate)) {
          _tmp_4 = null;
        } else {
          _tmp_4 = _cursor.getLong(_cursorIndexOfExpireDate);
        }
        _tmpExpireDate = __converters.fromTimestamp(_tmp_4);
        final String _tmpPhotoUri;
        if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
          _tmpPhotoUri = null;
        } else {
          _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
        }
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item = new Ingredient(_tmpId,_tmpName,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpAddDate,_tmpExpireDate,_tmpPhotoUri,_tmpNotes);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Ingredient> getAllIngredientsByCategory() {
    final String _sql = "SELECT * FROM ingredients ORDER BY category ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
      final int _cursorIndexOfAddDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addDate");
      final int _cursorIndexOfExpireDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expireDate");
      final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final List<Ingredient> _result = new ArrayList<Ingredient>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Ingredient _item;
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final IngredientCategory _tmpCategory;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfCategory);
        }
        _tmpCategory = __converters.toCategory(_tmp);
        final float _tmpQuantity;
        _tmpQuantity = _cursor.getFloat(_cursorIndexOfQuantity);
        final String _tmpUnit;
        if (_cursor.isNull(_cursorIndexOfUnit)) {
          _tmpUnit = null;
        } else {
          _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
        }
        final Date _tmpAddDate;
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfAddDate)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfAddDate);
        }
        _tmpAddDate = __converters.fromTimestamp(_tmp_1);
        final Date _tmpExpireDate;
        final Long _tmp_2;
        if (_cursor.isNull(_cursorIndexOfExpireDate)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getLong(_cursorIndexOfExpireDate);
        }
        _tmpExpireDate = __converters.fromTimestamp(_tmp_2);
        final String _tmpPhotoUri;
        if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
          _tmpPhotoUri = null;
        } else {
          _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
        }
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item = new Ingredient(_tmpId,_tmpName,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpAddDate,_tmpExpireDate,_tmpPhotoUri,_tmpNotes);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Ingredient getIngredientById(final long id) {
    final String _sql = "SELECT * FROM ingredients WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
      final int _cursorIndexOfAddDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addDate");
      final int _cursorIndexOfExpireDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expireDate");
      final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final Ingredient _result;
      if(_cursor.moveToFirst()) {
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final IngredientCategory _tmpCategory;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfCategory);
        }
        _tmpCategory = __converters.toCategory(_tmp);
        final float _tmpQuantity;
        _tmpQuantity = _cursor.getFloat(_cursorIndexOfQuantity);
        final String _tmpUnit;
        if (_cursor.isNull(_cursorIndexOfUnit)) {
          _tmpUnit = null;
        } else {
          _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
        }
        final Date _tmpAddDate;
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfAddDate)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfAddDate);
        }
        _tmpAddDate = __converters.fromTimestamp(_tmp_1);
        final Date _tmpExpireDate;
        final Long _tmp_2;
        if (_cursor.isNull(_cursorIndexOfExpireDate)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getLong(_cursorIndexOfExpireDate);
        }
        _tmpExpireDate = __converters.fromTimestamp(_tmp_2);
        final String _tmpPhotoUri;
        if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
          _tmpPhotoUri = null;
        } else {
          _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
        }
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _result = new Ingredient(_tmpId,_tmpName,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpAddDate,_tmpExpireDate,_tmpPhotoUri,_tmpNotes);
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
  public LiveData<List<Ingredient>> getIngredientsByCategory(final IngredientCategory category) {
    final String _sql = "SELECT * FROM ingredients WHERE category = ? ORDER BY expireDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromCategory(category);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"ingredients"}, false, new Callable<List<Ingredient>>() {
      @Override
      public List<Ingredient> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfAddDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addDate");
          final int _cursorIndexOfExpireDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expireDate");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Ingredient> _result = new ArrayList<Ingredient>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Ingredient _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final IngredientCategory _tmpCategory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.toCategory(_tmp_1);
            final float _tmpQuantity;
            _tmpQuantity = _cursor.getFloat(_cursorIndexOfQuantity);
            final String _tmpUnit;
            if (_cursor.isNull(_cursorIndexOfUnit)) {
              _tmpUnit = null;
            } else {
              _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            }
            final Date _tmpAddDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfAddDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfAddDate);
            }
            _tmpAddDate = __converters.fromTimestamp(_tmp_2);
            final Date _tmpExpireDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfExpireDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfExpireDate);
            }
            _tmpExpireDate = __converters.fromTimestamp(_tmp_3);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new Ingredient(_tmpId,_tmpName,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpAddDate,_tmpExpireDate,_tmpPhotoUri,_tmpNotes);
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
  public LiveData<List<Ingredient>> getIngredientsExpiringBefore(final Date date) {
    final String _sql = "SELECT * FROM ingredients WHERE expireDate <= ? ORDER BY expireDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"ingredients"}, false, new Callable<List<Ingredient>>() {
      @Override
      public List<Ingredient> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfAddDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addDate");
          final int _cursorIndexOfExpireDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expireDate");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Ingredient> _result = new ArrayList<Ingredient>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Ingredient _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final IngredientCategory _tmpCategory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.toCategory(_tmp_1);
            final float _tmpQuantity;
            _tmpQuantity = _cursor.getFloat(_cursorIndexOfQuantity);
            final String _tmpUnit;
            if (_cursor.isNull(_cursorIndexOfUnit)) {
              _tmpUnit = null;
            } else {
              _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            }
            final Date _tmpAddDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfAddDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfAddDate);
            }
            _tmpAddDate = __converters.fromTimestamp(_tmp_2);
            final Date _tmpExpireDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfExpireDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfExpireDate);
            }
            _tmpExpireDate = __converters.fromTimestamp(_tmp_3);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new Ingredient(_tmpId,_tmpName,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpAddDate,_tmpExpireDate,_tmpPhotoUri,_tmpNotes);
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
  public LiveData<List<Ingredient>> getValidIngredients(final Date currentDate) {
    final String _sql = "SELECT * FROM ingredients WHERE expireDate IS NULL OR expireDate > ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(currentDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"ingredients"}, false, new Callable<List<Ingredient>>() {
      @Override
      public List<Ingredient> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfAddDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addDate");
          final int _cursorIndexOfExpireDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expireDate");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Ingredient> _result = new ArrayList<Ingredient>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Ingredient _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final IngredientCategory _tmpCategory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.toCategory(_tmp_1);
            final float _tmpQuantity;
            _tmpQuantity = _cursor.getFloat(_cursorIndexOfQuantity);
            final String _tmpUnit;
            if (_cursor.isNull(_cursorIndexOfUnit)) {
              _tmpUnit = null;
            } else {
              _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            }
            final Date _tmpAddDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfAddDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfAddDate);
            }
            _tmpAddDate = __converters.fromTimestamp(_tmp_2);
            final Date _tmpExpireDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfExpireDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfExpireDate);
            }
            _tmpExpireDate = __converters.fromTimestamp(_tmp_3);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new Ingredient(_tmpId,_tmpName,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpAddDate,_tmpExpireDate,_tmpPhotoUri,_tmpNotes);
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
  public List<Ingredient> getExpiringIngredients(final Date date) {
    final String _sql = "SELECT * FROM ingredients WHERE expireDate IS NOT NULL AND expireDate <= ? ORDER BY expireDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
      final int _cursorIndexOfAddDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addDate");
      final int _cursorIndexOfExpireDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expireDate");
      final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final List<Ingredient> _result = new ArrayList<Ingredient>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Ingredient _item;
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final IngredientCategory _tmpCategory;
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
        }
        _tmpCategory = __converters.toCategory(_tmp_1);
        final float _tmpQuantity;
        _tmpQuantity = _cursor.getFloat(_cursorIndexOfQuantity);
        final String _tmpUnit;
        if (_cursor.isNull(_cursorIndexOfUnit)) {
          _tmpUnit = null;
        } else {
          _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
        }
        final Date _tmpAddDate;
        final Long _tmp_2;
        if (_cursor.isNull(_cursorIndexOfAddDate)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getLong(_cursorIndexOfAddDate);
        }
        _tmpAddDate = __converters.fromTimestamp(_tmp_2);
        final Date _tmpExpireDate;
        final Long _tmp_3;
        if (_cursor.isNull(_cursorIndexOfExpireDate)) {
          _tmp_3 = null;
        } else {
          _tmp_3 = _cursor.getLong(_cursorIndexOfExpireDate);
        }
        _tmpExpireDate = __converters.fromTimestamp(_tmp_3);
        final String _tmpPhotoUri;
        if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
          _tmpPhotoUri = null;
        } else {
          _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
        }
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item = new Ingredient(_tmpId,_tmpName,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpAddDate,_tmpExpireDate,_tmpPhotoUri,_tmpNotes);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Ingredient>> searchIngredients(final String query) {
    final String _sql = "SELECT * FROM ingredients WHERE name LIKE '%' || ? || '%' ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"ingredients"}, false, new Callable<List<Ingredient>>() {
      @Override
      public List<Ingredient> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfAddDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addDate");
          final int _cursorIndexOfExpireDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expireDate");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Ingredient> _result = new ArrayList<Ingredient>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Ingredient _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final IngredientCategory _tmpCategory;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.toCategory(_tmp);
            final float _tmpQuantity;
            _tmpQuantity = _cursor.getFloat(_cursorIndexOfQuantity);
            final String _tmpUnit;
            if (_cursor.isNull(_cursorIndexOfUnit)) {
              _tmpUnit = null;
            } else {
              _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            }
            final Date _tmpAddDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfAddDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfAddDate);
            }
            _tmpAddDate = __converters.fromTimestamp(_tmp_1);
            final Date _tmpExpireDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfExpireDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfExpireDate);
            }
            _tmpExpireDate = __converters.fromTimestamp(_tmp_2);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new Ingredient(_tmpId,_tmpName,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpAddDate,_tmpExpireDate,_tmpPhotoUri,_tmpNotes);
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
  public int getIngredientCount() {
    final String _sql = "SELECT COUNT(*) FROM ingredients";
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

  @Override
  public int getExpiringCount(final Date date) {
    final String _sql = "SELECT COUNT(*) FROM ingredients WHERE expireDate IS NOT NULL AND expireDate <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
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

  @Override
  public LiveData<List<Ingredient>> getExpiredIngredients(final Date currentDate) {
    final String _sql = "SELECT * FROM ingredients WHERE expireDate IS NOT NULL AND expireDate < ? ORDER BY expireDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(currentDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"ingredients"}, false, new Callable<List<Ingredient>>() {
      @Override
      public List<Ingredient> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfAddDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addDate");
          final int _cursorIndexOfExpireDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expireDate");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Ingredient> _result = new ArrayList<Ingredient>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Ingredient _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final IngredientCategory _tmpCategory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.toCategory(_tmp_1);
            final float _tmpQuantity;
            _tmpQuantity = _cursor.getFloat(_cursorIndexOfQuantity);
            final String _tmpUnit;
            if (_cursor.isNull(_cursorIndexOfUnit)) {
              _tmpUnit = null;
            } else {
              _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            }
            final Date _tmpAddDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfAddDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfAddDate);
            }
            _tmpAddDate = __converters.fromTimestamp(_tmp_2);
            final Date _tmpExpireDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfExpireDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfExpireDate);
            }
            _tmpExpireDate = __converters.fromTimestamp(_tmp_3);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new Ingredient(_tmpId,_tmpName,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpAddDate,_tmpExpireDate,_tmpPhotoUri,_tmpNotes);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
