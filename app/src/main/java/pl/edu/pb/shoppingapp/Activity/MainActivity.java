package pl.edu.pb.shoppingapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import pl.edu.pb.shoppingapp.Fragment.FavouriteShopsFragment;
import pl.edu.pb.shoppingapp.Fragment.HomeFragment;
import pl.edu.pb.shoppingapp.Fragment.MapsFragment;
import pl.edu.pb.shoppingapp.Fragment.MoreFragment;
import pl.edu.pb.shoppingapp.NotificationViewModel;
import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static NotificationViewModel model;
    private ActivityMainBinding binding;

    private static final String TAG = "MAIN_ACTIVITY";
    private static final String TOPIC = "GENERAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model = ViewModelProviders.of(this).get(NotificationViewModel.class);

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

        //to be deleted in the future!
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                System.out.println(task.getResult());
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "Message received");
                }
                else{
                    Log.d(TAG, "Message receiving error!");
                }
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view, fragment);
        fragmentTransaction.commit();
    }
}