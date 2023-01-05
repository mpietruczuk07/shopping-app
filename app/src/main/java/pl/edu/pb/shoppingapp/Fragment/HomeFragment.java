package pl.edu.pb.shoppingapp.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import pl.edu.pb.shoppingapp.Activity.EditProductActivity;
import pl.edu.pb.shoppingapp.Product;
import pl.edu.pb.shoppingapp.ProductViewModel;
import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ProductViewModel productViewModel;
    private Product editedProduct;

    public static final int NEW_PRODUCT_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_PRODUCT_ACTIVITY_REQUEST_CODE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        final ProductAdapter adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.findAll().observe(getViewLifecycleOwner(), adapter::setProducts);

        FloatingActionButton addBookButton = view.findViewById(R.id.add_button);
        addBookButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProductActivity.class);
            startActivityForResult(intent, NEW_PRODUCT_ACTIVITY_REQUEST_CODE);
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_PRODUCT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Product product = new Product(data.getStringExtra(EditProductActivity.EXTRA_EDIT_PRODUCT_TITLE), 1, "TEST", false);
            productViewModel.insert(product);
            //snackbar!!!
        }

        if (requestCode == EDIT_PRODUCT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            editedProduct.setTitle(data.getStringExtra(EditProductActivity.EXTRA_EDIT_PRODUCT_TITLE));
//            editedProduct.setNote(data.getStringExtra(EditProductActivity.EXTRA_EDIT_PRODUCT_NOTE));
//            editedProduct.setNote(data.getStringExtra(EditProductActivity.EXTRA_EDIT_PRODUCT_NOTE));
            productViewModel.update(editedProduct);
            editedProduct = null;
            //snackbar!!!
        }
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

            productItem.setOnLongClickListener(v -> {
                productViewModel.delete(product);
                System.out.println("Skasowano");
                Snackbar.make(requireActivity().findViewById(R.id.recyclerview), getString(R.string.book_deleted), Snackbar.LENGTH_LONG).show();
                return true;
            });

            productItem.setOnClickListener(v -> {
                editedProduct = product;
                Intent intent = new Intent(getActivity(), EditProductActivity.class);
                intent.putExtra(EditProductActivity.EXTRA_EDIT_PRODUCT_TITLE, productTitleTextView.getText());
                //intent.putExtra(EditProductActivity.EXTRA_EDIT_PRODUCT_NOTE,  productNoteTextView.getText());
                startActivityForResult(intent, EDIT_PRODUCT_ACTIVITY_REQUEST_CODE);
            });

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
            } else {
                System.out.println("brak");
                Log.d("MainActivity", "No products");
            }
        }

        @Override
        public int getItemCount() {
            if (products != null) {
                return products.size();
            } else {
                return 0;
            }
        }

        void setProducts(List<Product> products) {
            this.products = products;
            notifyDataSetChanged();
        }
    }
}