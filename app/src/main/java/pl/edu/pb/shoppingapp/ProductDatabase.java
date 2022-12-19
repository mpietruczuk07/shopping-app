package pl.edu.pb.shoppingapp;

import static androidx.room.Room.*;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {
    private static ProductDatabase databaseInstance;
    static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    public abstract ProductDao productDao();

    static ProductDatabase getDatabase(final Context context) {
        if (databaseInstance == null) {
            databaseInstance = databaseBuilder(
                    context.getApplicationContext(), ProductDatabase.class, "product_database")
                    .addCallback(roomDatabaseCallback).build();
        }
        return databaseInstance;
    }

    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                ProductDao dao = databaseInstance.productDao();
                Product product = new Product("Warunek z baz danych", 1800, "Żart", false);
                dao.insert(product);
            });
        }
    };
}