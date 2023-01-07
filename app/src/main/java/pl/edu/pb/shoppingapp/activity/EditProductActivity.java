package pl.edu.pb.shoppingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.edu.pb.shoppingapp.R;

public class EditProductActivity extends AppCompatActivity {
    public static final String EXTRA_EDIT_PRODUCT_TITLE = "title";
    private EditText editTitleEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        editTitleEditText = findViewById(R.id.edit_product_title);

        if (getIntent().hasExtra(EXTRA_EDIT_PRODUCT_TITLE)) {
            editTitleEditText.setText(getIntent().getStringExtra(EXTRA_EDIT_PRODUCT_TITLE));
        }

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            String title = editTitleEditText.getText().toString();
            replyIntent.putExtra(EXTRA_EDIT_PRODUCT_TITLE, title);
            setResult(RESULT_OK, replyIntent);
            finish();
        });
    }
}
