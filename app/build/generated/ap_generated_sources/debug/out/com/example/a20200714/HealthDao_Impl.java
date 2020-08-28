package com.example.a20200714;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class HealthDao_Impl implements HealthDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HealthResult> __insertionAdapterOfHealthResult;

  private final EntityDeletionOrUpdateAdapter<HealthResult> __deletionAdapterOfHealthResult;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public HealthDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHealthResult = new EntityInsertionAdapter<HealthResult>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `HealthResult` (`title`,`health`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HealthResult value) {
        if (value.title == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.title);
        }
        if (value.health == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.health);
        }
      }
    };
    this.__deletionAdapterOfHealthResult = new EntityDeletionOrUpdateAdapter<HealthResult>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `HealthResult` WHERE `title` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HealthResult value) {
        if (value.title == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.title);
        }
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM healthresult";
        return _query;
      }
    };
  }

  @Override
  public void insert(final HealthResult hr) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfHealthResult.insert(hr);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final HealthResult hr) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfHealthResult.handle(hr);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clearAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfClearAll.release(_stmt);
    }
  }

  @Override
  public List<HealthResult> getAll() {
    final String _sql = "SELECT * FROM healthresult";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfHealth = CursorUtil.getColumnIndexOrThrow(_cursor, "health");
      final List<HealthResult> _result = new ArrayList<HealthResult>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final HealthResult _item;
        _item = new HealthResult();
        _item.title = _cursor.getString(_cursorIndexOfTitle);
        _item.health = _cursor.getString(_cursorIndexOfHealth);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<HealthResult> loadAllByIds(final String titles) {
    final String _sql = "SELECT * FROM healthresult WHERE title IN (?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (titles == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, titles);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfHealth = CursorUtil.getColumnIndexOrThrow(_cursor, "health");
      final List<HealthResult> _result = new ArrayList<HealthResult>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final HealthResult _item;
        _item = new HealthResult();
        _item.title = _cursor.getString(_cursorIndexOfTitle);
        _item.health = _cursor.getString(_cursorIndexOfHealth);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
