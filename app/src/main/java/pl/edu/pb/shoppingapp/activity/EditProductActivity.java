package pl.edu.pb.shoppingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.ActivityEditProductBinding;

public class EditProductActivity extends AppCompatActivity {
    public static final String EXTRA_EDIT_PRODUCT_TITLE = "title";
    private ActivityEditProductBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().hasExtra(EXTRA_EDIT_PRODUCT_TITLE)) {
            binding.editProductTitle.setText(getIntent().getStringExtra(EXTRA_EDIT_PRODUCT_TITLE));
            binding.spinner.setSelection(1);
        }

        binding.buttonSave.setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            String title = binding.editProductTitle.getText().toString();
            replyIntent.putExtra(EXTRA_EDIT_PRODUCT_TITLE, title);
            setResult(RESULT_OK, replyIntent);
            finish();
        });
    }
}
