package pl.edu.pb.shoppingapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.edu.pb.shoppingapp.databinding.FragmentMapsBinding;

public class MapsFragment extends Fragment {
    private FragmentMapsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMapsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}