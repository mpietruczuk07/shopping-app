package pl.edu.pb.shoppingapp.Fragment;

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

import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.FragmentEmailChangeBinding;

public class EmailChangeFragment extends Fragment {
    private FragmentEmailChangeBinding fragmentEmailChangeBinding;
    private final static String TAG = "fragment_email_change";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentEmailChangeBinding = FragmentEmailChangeBinding.inflate(inflater, container, false);
        return fragmentEmailChangeBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentEmailChangeBinding.updateEmailBtn.setOnClickListener(v -> {
            String email = fragmentEmailChangeBinding.inputEmailEdit.getText().toString().trim();
            if (email.isEmpty()) {
                fragmentEmailChangeBinding.inputEmailEdit.setError(getText(R.string.email_required));
                fragmentEmailChangeBinding.inputEmailEdit.requestFocus();
            }
            else {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                firebaseUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                            Map<String, Object> map = new HashMap<>();
                            databaseReference.updateChildren(map);
                            Toast.makeText(requireContext(), getText(R.string.email_updated), Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(v).popBackStack();
                        } else {
                            Log.d("TAG", "onComplete: " + task.getException());
                            Toast.makeText(requireContext(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
