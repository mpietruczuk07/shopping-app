package pl.edu.pb.shoppingapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductRepository {
    private final ProductDao productDao;
    private final LiveData<List<Product>> products;

    ProductRepository(Application application) {
        ProductDatabase database = ProductDatabase.getDatabase(application);
        productDao = database.productDao();
        products = productDao.findAll();
    }

    LiveData<List<Product>> findAllProducts() {
        return products;
    }

    void insert (Product product) {
        ProductDatabase.databaseWriteExecutor.execute(() -> productDao.insert(product));
    }

    void update(Product product) {
        ProductDatabase.databaseWriteExecutor.execute(() -> productDao.update(product));
    }

    void delete(Product product) {
        ProductDatabase.databaseWriteExecutor.execute(() -> productDao.delete(product));
    }
}