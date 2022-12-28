package pl.edu.pb.shoppingapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Map;

import pl.edu.pb.shoppingapp.Fragment.FavouriteShopsFragment;
import pl.edu.pb.shoppingapp.Fragment.HomeFragment;
import pl.edu.pb.shoppingapp.Fragment.MapsFragment;
import pl.edu.pb.shoppingapp.Fragment.MoreFragment;
import pl.edu.pb.shoppingapp.Product;
import pl.edu.pb.shoppingapp.ProductViewModel;
import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private Product editedProduct;
    private ActivityMainBinding binding;

    public static final int NEW_PRODUCT_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_PRODUCT_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavMenu.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_btn:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.maps_btn:
                    replaceFragment(new MapsFragment());
                    break;
                case R.id.favourite_shops_btn:
                    replaceFragment(new FavouriteShopsFragment());
                    break;
                case R.id.more_btn:
                    replaceFragment(new MoreFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view, fragment);
        fragmentTransaction.commit();
    }

//        RecyclerView recyclerView = findViewById(R.id.recyclerview);
//
//        final ProductAdapter adapter = new ProductAdapter();
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
//        productViewModel.findAll().observe(this, adapter::setProducts);
//
//        FloatingActionButton addBookButton = findViewById(R.id.add_button);
//        addBookButton.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, EditProductActivity.class);
//            startActivityForResult(intent, NEW_PRODUCT_ACTIVITY_REQUEST_CODE);
//        });
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == NEW_PRODUCT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            Product product = new Product(data.getStringExtra(EditProductActivity.EXTRA_EDIT_PRODUCT_TITLE), 1, "TEST", false);
//            productViewModel.insert(product);
//            //snackbar!!!
//        }
//
//        if (requestCode == EDIT_PRODUCT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            editedProduct.setTitle(data.getStringExtra(EditProductActivity.EXTRA_EDIT_PRODUCT_TITLE));
////            editedProduct.setNote(data.getStringExtra(EditProductActivity.EXTRA_EDIT_PRODUCT_NOTE));
////            editedProduct.setNote(data.getStringExtra(EditProductActivity.EXTRA_EDIT_PRODUCT_NOTE));
//            productViewModel.update(editedProduct);
//            editedProduct = null;
//            //snackbar!!!
//        }
//    }
//
//    private class ProductHolder extends RecyclerView.ViewHolder {
//        private TextView productTitleTextView;
//        private TextView productQuantityTextView;
//        private Product product;
//
//        public ProductHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.product_item_list, parent, false));
//
//            productTitleTextView = itemView.findViewById((R.id.product_title));
//            productQuantityTextView = itemView.findViewById((R.id.product_quantity));
//
//            View productItem = itemView.findViewById(R.id.product_item);
//
//            productItem.setOnLongClickListener(v -> {
//                productViewModel.delete(product);
//                System.out.println("Skasowano");
//                Snackbar.make(findViewById(R.id.main_view), getString(R.string.book_deleted), Snackbar.LENGTH_LONG).show();
//                return true;
//            });
//
//            productItem.setOnClickListener(v -> {
//                editedProduct = product;
//                Intent intent = new Intent(MainActivity.this, EditProductActivity.class);
//                intent.putExtra(EditProductActivity.EXTRA_EDIT_PRODUCT_TITLE, productTitleTextView.getText());
//                //intent.putExtra(EditProductActivity.EXTRA_EDIT_PRODUCT_NOTE,  productNoteTextView.getText());
//                startActivityForResult(intent, EDIT_PRODUCT_ACTIVITY_REQUEST_CODE);
//            });
//
//        }
//
//        public void bind(Product product) {
//            productTitleTextView.setText(product.getTitle());
//            productQuantityTextView.setText(Integer.toString(product.getQuantity()));
//            this.product = product;
//        }
//    }
//
//    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
//        private List<Product> products;
//
//        @NonNull
//        @Override
//        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return new ProductHolder(getLayoutInflater(), parent);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
//            if (products != null) {
//                Product book = products.get(position);
//                holder.bind(book);
//            } else {
//                System.out.println("brak");
//                Log.d("MainActivity", "No products");
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            if (products != null) {
//                return products.size();
//            } else {
//                return 0;
//            }
//        }
//
//        void setProducts(List<Product> products) {
//            this.products = products;
//            notifyDataSetChanged();
//        }
//    }
    }