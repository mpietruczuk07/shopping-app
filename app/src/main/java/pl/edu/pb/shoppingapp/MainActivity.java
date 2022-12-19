package pl.edu.pb.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ProductAdapter adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.findAll().observe(this, adapter::setProducts);
    }

    private class ProductHolder extends RecyclerView.ViewHolder {
        private TextView productTitleTextView;
        private TextView productQuantityTextView;
        private Product product;

        public ProductHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.product_item_list, parent, false));

            productTitleTextView = itemView.findViewById((R.id.product_title));
            productQuantityTextView = itemView.findViewById((R.id.product_quantity));

            View productItem = itemView.findViewById(R.id.product_item);
            if (productItem != null) {
                productItem.setOnLongClickListener(v -> {
                    productViewModel.delete(product);
                    Snackbar.make(findViewById(R.id.main_view), getString(R.string.book_deleted), Snackbar.LENGTH_LONG).show();
                    return true;
                });
                // on click do detali
            }
        }

        public void bind(Product product) {
            productTitleTextView.setText(product.getTitle());
            productQuantityTextView.setText(Integer.toString(product.getQuantity()));
            this.product = product;
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
        private List<Product> products;

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProductHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
            if (products != null) {
                Product book = products.get(position);
                holder.bind(book);
            }
            else {
                Log.d("MainActivity", "No products");
            }
        }

        @Override
        public int getItemCount() {
            if ( products != null) {
                return products.size();
            }
            else {
                return 0;
            }
        }

        void setProducts(List<Product> products) {
            this.products = products;
            notifyDataSetChanged();
        }
    }
}