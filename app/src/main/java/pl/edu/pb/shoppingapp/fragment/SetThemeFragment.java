package pl.edu.pb.shoppingapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.FragmentSetThemeBinding;

public class SetThemeFragment extends Fragment {
    private FragmentSetThemeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSetThemeBinding.inflate(inflater, container, false);

        int selectedDarkMode = requireActivity()
                .getSharedPreferences("DARK_MODE", Context.MODE_PRIVATE)
                .getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        switch (selectedDarkMode) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                binding.radioLight.setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                binding.radioDark.setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                binding.radioSystem.setChecked(true);
                break;
        }

        binding.backBtn.setOnClickListener(v-> getActivity().onBackPressed());

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            SharedPreferences.Editor editor = requireActivity()
                    .getSharedPreferences("DARK_MODE", Context.MODE_PRIVATE).edit();

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_light:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        editor.putInt("theme_mode", AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                    case R.id.radio_dark:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor.putInt("theme_mode", AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case R.id.radio_system:
                        AppCompatDelegate
                                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        editor.putInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        break;
                }
                editor.apply();
            }
        });

        return binding.getRoot();
    }
}