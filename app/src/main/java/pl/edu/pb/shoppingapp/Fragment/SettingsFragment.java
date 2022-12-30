package pl.edu.pb.shoppingapp.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        if (!requireActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            binding.cardBiometrics.setVisibility(View.GONE);
        } else {
            boolean isAppLocked = requireActivity()
                    .getSharedPreferences("user_biometrics", Context.MODE_PRIVATE)
                    .getBoolean("biometrics", false);
            if (isAppLocked) {
                binding.biometricSwitch.setChecked(true);
            }
        }

        binding.biometricSwitch.setOnCheckedChangeListener((switchView, isChecked) -> {
            SharedPreferences.Editor editor = requireActivity()
                    .getSharedPreferences("user_biometrics", Context.MODE_PRIVATE).edit();
            if (isChecked) {
                editor.putBoolean("biometrics", true);
            } else {
                editor.putBoolean("biometrics", false);
            }
            editor.apply();
        });

        binding.backBtn.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        binding.usernameText.setOnClickListener(v -> {
            usernameDialog();
        });

        return binding.getRoot();
    }

    private void usernameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.change_username_dialog_layout, null, false);
        builder.setView(view);
        TextInputEditText editUsername = view.findViewById(R.id.input_edit_username);
        builder.setTitle(getText(R.string.edit_username));
        builder.setPositiveButton(getText(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String username = editUsername.getText().toString().trim();
                if (!username.isEmpty()) {
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                    firebaseAuth.getCurrentUser().updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                                Map<String, Object> map = new HashMap<>();
                                map.put("username", username);
                                databaseReference.child(firebaseAuth.getUid()).updateChildren(map);
                                binding.usernameText.setText(username);
                                Snackbar.make(requireActivity().findViewById(R.id.settings_linear_layout), getText(R.string.username_updated), Snackbar.LENGTH_LONG).show();
                            }
                            else {
                                Snackbar.make(requireActivity().findViewById(R.id.settings_linear_layout), getText(R.string.username_error), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Snackbar.make(requireActivity().findViewById(R.id.settings_linear_layout), getText(R.string.username_required), Snackbar.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton(getText(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create().show();
    }
}