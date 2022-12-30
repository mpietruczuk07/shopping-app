package pl.edu.pb.shoppingapp.Fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationRequest;
import android.os.Bundle;

import androidx.activity.result.IntentSenderRequest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.errors.ApiException;

import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.FragmentMapsBinding;

public class MapsFragment extends Fragment {
    private FragmentMapsBinding binding;
    private GoogleMap mGoogleMap;
    private Boolean isLocationPermission;
    private LocationRequest locationRequest;
    private LocationCallback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMapsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isLocationPermission) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.fragment_map);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat
                .checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                checkGPS();
                return false;
            }
        });
    }

    public void checkGPS() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true);
        Task<LocationSettingsResponse> locationSettingsResponseTask = LocationServices
                .getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());
        locationSettingsResponseTask.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
               @Override
               public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                   try {
                       LocationSettingsResponse response = task.getResult(ApiException.class);
                   }
                   catch (ApiException e) {
                       if(e.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                           ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                           try {
                               resolvableApiException.startResolutionForResult(getActivity(), 101);
                           }
                           catch (IntentSender.SendIntentException sendIntentException) {
                               sendIntentException.printStackTrace();
                           }
                       }
                       if(e.getStatusCode() == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE) {
                           Toast.makeText(getActivity(), "Unavaliable", Toast.LENGTH_SHORT).show();
                       }
                   }
               }
           });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(getContext(), "Enabled", Toast.LENGTH_SHORT).show();
            }
            if(resultCode == RESULT_CANCELED) {
                Toast.makeText(getContext(), "Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}