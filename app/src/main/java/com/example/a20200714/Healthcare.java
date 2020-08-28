package com.example.a20200714;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

@Entity
class HealthResult {
    @PrimaryKey @NonNull
    String title = "";
    String health;
}
//public class Healthcare {
    @Dao
    interface HealthDao{
        @Query("SELECT * FROM healthresult")
        List<HealthResult> getAll();

        @Query("DELETE FROM healthresult")
        void clearAll();

        @Query("SELECT * FROM healthresult WHERE title IN (:titles)")
        List<HealthResult> loadAllByIds(String titles);

     //   @Insert
      //  void insert(HealthResult hr);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(HealthResult hr);

        @Delete
        void delete(HealthResult hr);
    }
    @Database(entities = {HealthResult.class}, version = 1, exportSchema = false)
    abstract class HealthDatabase extends RoomDatabase {
        private static volatile HealthDatabase INSTANCE;

        abstract HealthDao healthDao();

        public static HealthDatabase getInstance(Context context) {

            if (INSTANCE == null) {
                synchronized (HealthDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                HealthDatabase.class, "Health.db").allowMainThreadQueries().build();
                    }
                }
            }
            return INSTANCE;
        }

    }
//}