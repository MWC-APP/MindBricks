package ch.inf.usi.mindbricks.ui.onboarding.page;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ch.inf.usi.mindbricks.R;

public class OnboardingNotificationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding_notifications, container, false);

        Button button = view.findViewById(R.id.buttonEnableNotifications);
        button.setOnClickListener(v -> requestNotificationPermissionIfNeeded());

        return view;
    }

    private void requestNotificationPermissionIfNeeded() {
        System.err.println("This needs to be implemented.");
    }
}