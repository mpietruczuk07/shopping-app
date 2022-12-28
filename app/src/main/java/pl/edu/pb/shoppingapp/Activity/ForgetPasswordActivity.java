package pl.edu.pb.shoppingapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.ActivityForgetPasswordBinding;

public class ForgetPasswordActivity extends AppCompatActivity {
    private ActivityForgetPasswordBinding binding;
    private FirebaseAuth firebaseAuth;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.forgetBtn.setOnClickListener(v -> {
            resetPassword();
        });
    }

    private void resetPassword() {
        firebaseAuth = FirebaseAuth.getInstance();
        email = binding.emailFieldReset.getText().toString().trim();
        if (email.isEmpty()) {
            binding.emailFieldReset.setError(getText(R.string.email_required));
            binding.emailFieldReset.requestFocus();
        }
        else {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(ForgetPasswordActivity.this,
                                getText(R.string.reset_email_sent), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                    else {
                        Toast.makeText(ForgetPasswordActivity.this, getText(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}