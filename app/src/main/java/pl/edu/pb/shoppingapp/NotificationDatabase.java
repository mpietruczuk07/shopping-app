package pl.edu.pb.shoppingapp;

import static androidx.room.Room.*;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Notification.class}, version = 1, exportSchema = false)
public abstract class NotificationDatabase extends RoomDatabase {
    private static NotificationDatabase databaseInstance;
    static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    public abstract NotificationDao notificationDao();

    static NotificationDatabase getDatabase(final Context context) {
        if (databaseInstance == null) {
            databaseInstance = databaseBuilder(
                    context.getApplicationContext(), NotificationDatabase.class, "notification_database")
                    .addCallback(roomDatabaseCallback).build();
        }
        return databaseInstance;
    }

    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                NotificationDao dao = databaseInstance.notificationDao();
                Notification notification = new Notification("Test 1",  "This is the first notification");
                dao.insert(notification);
            });
        }
    };
}