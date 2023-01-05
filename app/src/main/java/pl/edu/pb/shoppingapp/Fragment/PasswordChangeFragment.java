package pl.edu.pb.shoppingapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import pl.edu.pb.shoppingapp.Activity.LoginActivity;
import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.FragmentEmailChangeBinding;
import pl.edu.pb.shoppingapp.databinding.FragmentPasswordChangeBinding;


public class PasswordChangeFragment extends Fragment {
    private FragmentPasswordChangeBinding binding;
    private final static String TAG = "PASSWORD_CHANGE_FRAGMENT";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPasswordChangeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backBtn.setOnClickListener(v -> getActivity().onBackPressed());

        binding.updatePasswordBtn.setOnClickListener(v -> {
            String password = binding.inputPasswordEdit.getText().toString().trim();

            if (password.isEmpty()) {
                binding.inputPasswordEdit.setError(getText(R.string.password_required));
                binding.inputPasswordEdit.requestFocus();
            } else if (password.length() < 8) {
                binding.inputPasswordEdit.setError(getText(R.string.password_too_short));
                binding.inputPasswordEdit.requestFocus();
            } else {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                firebaseUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), getText(R.string.password_updated), Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        } else {
                            Log.d(TAG, "onComplete: " + task.getException());
                            Toast.makeText(requireContext(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
