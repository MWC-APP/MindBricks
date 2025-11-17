package ch.inf.usi.mindbricks.ui.onboarding.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import ch.inf.usi.mindbricks.R;
import ch.inf.usi.mindbricks.util.PreferencesManager;

public class OnboardingUserFragment extends Fragment {

    private TextInputEditText editName;
    private TextInputEditText editFocusGoal;
    private TextInputEditText editSprintLength;
    private PreferencesManager prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding_user, container, false);

        prefs = new PreferencesManager(requireContext());

        editName = view.findViewById(R.id.editName);
        editFocusGoal = view.findViewById(R.id.editFocusGoal);
        editSprintLength = view.findViewById(R.id.editSprintLength);
        MaterialButton choosePhoto = view.findViewById(R.id.buttonChoosePhoto);

        choosePhoto.setOnClickListener(v -> launchPhotoPicker());

        // preload if already stored
        editName.setText(prefs.getUserName());
        editFocusGoal.setText(prefs.getUserFocusGoal());
        editSprintLength.setText(prefs.getUserSprintLengthMinutes());

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        String name = readText(editName);
        String focusGoal = readText(editFocusGoal);
        String sprintLength = readText(editSprintLength);

        // store the user information
        prefs.setUserName(name);
        prefs.setUserFocusGoal(focusGoal);
        prefs.setUserSprintLengthMinutes(sprintLength);
    }

    private void launchPhotoPicker() {
    }

    private String readText(TextInputEditText editText) {
        return editText.getText() != null ? editText.getText().toString().trim() : "";
    }
}
