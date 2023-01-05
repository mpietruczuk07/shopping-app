package pl.edu.pb.shoppingapp.Fragment;

import android.content.Intent;
import android.net.Uri;
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

public class EmailChangeFragment extends Fragment {
    private FragmentEmailChangeBinding binding;
    private final static String TAG = "EMAIL_CHANGE_FRAGMENT";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmailChangeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backBtn.setOnClickListener(v -> getActivity().onBackPressed());

        binding.updateEmailBtn.setOnClickListener(v -> {
            String email = binding.inputEmailEdit.getText().toString().trim();

            if (email.isEmpty()) {
                binding.inputEmailEdit.setError(getText(R.string.email_required));
                binding.inputEmailEdit.requestFocus();
            } else {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                firebaseUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                            Map<String, Object> map = new HashMap<>();
                            databaseReference.updateChildren(map);
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(requireContext(), getText(R.string.email_updated), Toast.LENGTH_LONG).show();
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
