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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import pl.edu.pb.shoppingapp.R;
import pl.edu.pb.shoppingapp.databinding.FragmentAuthenticationBinding;

public class AuthenticationFragment extends Fragment {
        private FragmentAuthenticationBinding binding;
        private AuthenticationFragmentArgs authenticationFragmentArgs;
        private boolean isPassword;
        private String email, password;
        private FirebaseAuth firebaseAuth;
        private final static String TAG = "fragment_authentication";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            binding = FragmentAuthenticationBinding.inflate(inflater, container, false);
            authenticationFragmentArgs = AuthenticationFragmentArgs.fromBundle(requireArguments());
            isPassword = authenticationFragmentArgs.getIsPassword();
            firebaseAuth = FirebaseAuth.getInstance();

            return binding.getRoot();
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            binding.inputEditEmail.setText(firebaseAuth.getCurrentUser().getEmail());

            binding.confirmEditBtn.setOnClickListener(v -> {
                if (email.isEmpty()) {
                    binding.inputEditEmail.setError(getText(R.string.email_required));
                    binding.inputEditEmail.requestFocus();
                } else if (password.isEmpty()) {
                    binding.inputEditPassword.setError(getText(R.string.password_required));
                    binding.inputEditPassword.requestFocus();
                } else if (password.length() < 8) {
                    binding.inputEditPassword.setError(getText(R.string.password_too_short));
                    binding.inputEditPassword.requestFocus();
                }
                else {
                    AuthCredential authCredential = EmailAuthProvider.getCredential(email, password);
                    firebaseAuth.getCurrentUser().reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                if (isPassword) {
                                    Navigation.findNavController(view).navigate(R.id.action_authenticationFragment_to_passwordChangeFragment);
                                } else {
                                    Navigation.findNavController(view).navigate(R.id.action_authenticationFragment_to_emailChangeFragment);
                                }
                            } else {
                                Log.d("TAG", "onComplete: " + task.getException());
                                Toast.makeText(requireContext(), "Error:" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
}