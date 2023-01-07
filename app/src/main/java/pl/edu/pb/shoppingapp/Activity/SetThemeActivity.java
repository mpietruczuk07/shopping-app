package pl.edu.pb.shoppingapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.ActivitySetThemeBinding;

public class SetThemeActivity extends AppCompatActivity {
    private ActivitySetThemeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetThemeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int selectedDarkMode = getSharedPreferences("DARK_MODE", Context.MODE_PRIVATE)
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

        binding.backBtn.setOnClickListener(v-> onBackPressed());

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            SharedPreferences.Editor editor =
                    getSharedPreferences("DARK_MODE", Context.MODE_PRIVATE).edit();

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

        binding.bottomNavMenu.setSelectedItemId(R.id.more_btn);

        //to do
        binding.bottomNavMenu.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_btn:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;
                    case R.id.maps_btn:
                    case R.id.search_btn:
                    case R.id.more_btn:
                    case R.id.favourite_shops_btn:

                        break;
                }
                return true;
            }
        });
    }
}