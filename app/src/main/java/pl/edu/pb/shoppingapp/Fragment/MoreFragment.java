package pl.edu.pb.shoppingapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import pl.edu.pb.shoppingapp.Activity.LoginActivity;
import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.FragmentMoreBinding;

public class MoreFragment extends Fragment {
    private FragmentMoreBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMoreBinding.inflate(getLayoutInflater());

        binding.cardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SettingsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_view, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.cardLogout.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getContext(), getText(R.string.logged_out_text), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

        return binding.getRoot();
    }
}