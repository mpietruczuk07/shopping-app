package pl.edu.pb.shoppingapp.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

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
                    .getSharedPreferences("user", Context.MODE_PRIVATE).edit();
            if (isChecked) {
                editor.putBoolean("biometrics", true);
            } else {
                editor.putBoolean("biometrics", false);
            }
            editor.apply();
        });

        return binding.getRoot();
    }
}