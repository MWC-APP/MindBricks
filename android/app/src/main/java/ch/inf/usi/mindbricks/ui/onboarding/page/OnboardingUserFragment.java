package ch.inf.usi.mindbricks.ui.onboarding.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ch.inf.usi.mindbricks.R;
import ch.inf.usi.mindbricks.util.PreferencesManager;

public class OnboardingUserFragment extends Fragment {

    private EditText editName;
    private EditText editSurname;
    private PreferencesManager prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding_user, container, false);

        prefs = new PreferencesManager(requireContext());

        editName = view.findViewById(R.id.editName);
        editSurname = view.findViewById(R.id.editSurname);

        // preload if already stored
        editName.setText(prefs.getUserName());
        editSurname.setText(prefs.getUserSurname());

        // TODO: handle profile picture picking on buttonChoosePhoto click

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        String name = editName.getText().toString().trim();
        String surname = editSurname.getText().toString().trim();

        // store the user information
        prefs.setUserName(name);
        prefs.setUserSurname(surname);
    }
}