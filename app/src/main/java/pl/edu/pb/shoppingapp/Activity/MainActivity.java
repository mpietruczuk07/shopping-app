package pl.edu.pb.shoppingapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import pl.edu.pb.shoppingapp.Fragment.FavouriteShopsFragment;
import pl.edu.pb.shoppingapp.Fragment.HomeFragment;
import pl.edu.pb.shoppingapp.Fragment.MapsFragment;
import pl.edu.pb.shoppingapp.Fragment.MoreFragment;
import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavMenu.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_btn:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.maps_btn:
                    replaceFragment(new MapsFragment());
                    break;
                case R.id.favourite_shops_btn:
                    replaceFragment(new FavouriteShopsFragment());
                    break;
                case R.id.more_btn:
                    replaceFragment(new MoreFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view, fragment);
        fragmentTransaction.commit();
    }
}