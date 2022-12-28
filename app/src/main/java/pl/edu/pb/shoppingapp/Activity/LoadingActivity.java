package pl.edu.pb.shoppingapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

import pl.edu.pb.shoppingapp.R;

public class LoadingActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(LoadingActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if(errorCode == 11) {
                    Toast.makeText(LoadingActivity.this, getText(R.string.error_11), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(LoadingActivity.this, getText(R.string.error), Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getText(R.string.biometric_title))
                .setDescription(getText(R.string.biometric_description))
                .setDeviceCredentialAllowed(true).build();
        firebaseAuth = FirebaseAuth.getInstance();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firebaseAuth.getCurrentUser() != null) {
                    boolean finger = getSharedPreferences("User", Context.MODE_PRIVATE)
                            .getBoolean("finger", true);
                    if(finger) {
                        biometricPrompt.authenticate(promptInfo);
                    }
                    else {
                        Toast.makeText(LoadingActivity.this, getText(R.string.error), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                        finish();
                    }
                }
                startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }
}