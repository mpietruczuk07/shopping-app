package pl.edu.pb.shoppingapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import pl.edu.pb.shoppingapp.activity.LoginActivity;
import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.FragmentMoreBinding;

public class MoreFragment extends Fragment {
    private FragmentMoreBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMoreBinding.inflate(getLayoutInflater());
        firebaseAuth = FirebaseAuth.getInstance();

        binding.backBtn.setOnClickListener(v-> getActivity().onBackPressed());

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

        binding.cardNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NotificationsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_view, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.cardHelp.setOnClickListener(v->{
            startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:shoppingapp@gmail.com")));
        });

        binding.cardInfo.setOnClickListener(v->infoDialog());

        binding.cardLogout.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getContext(), getText(R.string.logged_out_text), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.emailText.setText(firebaseAuth.getCurrentUser().getEmail());
        binding.usernameText.setText(firebaseAuth.getCurrentUser().getDisplayName());
    }

    private void infoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater
                .from(requireContext())
                .inflate(R.layout.info_dialog_layout, null, false);
        builder.setView(view);
        builder.setTitle(getText(R.string.app_info));

        builder.setNegativeButton(getText(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        }).create().show();
    }
}