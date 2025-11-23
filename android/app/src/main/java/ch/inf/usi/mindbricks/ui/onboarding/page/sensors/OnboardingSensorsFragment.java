package ch.inf.usi.mindbricks.ui.onboarding.page.sensors;

import android.Manifest;
import android.content.Context;
import android.content.res.ColorStateList;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import ch.inf.usi.mindbricks.R;
import ch.inf.usi.mindbricks.ui.onboarding.OnboardingStepValidator;
import ch.inf.usi.mindbricks.util.PermissionManager;

public class OnboardingSensorsFragment extends Fragment implements OnboardingStepValidator {

    private OnboardingSensorsViewModel viewModel;

    private PermissionManager.PermissionRequest micPermissionRequest;
    private boolean isMicPermissionDirty = false;

    private View rootView;
    private MaterialButton micButton;
    private MaterialButton lightButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // create view model
        viewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(OnboardingSensorsViewModel.class);

        // Create permission manager for microphone
        micPermissionRequest = PermissionManager.registerSinglePermission(
                this, Manifest.permission.RECORD_AUDIO,
                // on granted callback
                () -> {
                    viewModel.setHasRecordingPermission(true);
                    System.out.println("Microphone access granted");
                },
                // on denied callback
                () -> {
                    viewModel.setHasRecordingPermission(false);
                    System.out.println("Microphone access denied");
                },
                // on rationale callback
                () -> {
                    System.out.println("Microphone access rationale");
                }
        );

        rootView = inflater.inflate(R.layout.fragment_onboarding_sensors, container, false);

        micButton = rootView.findViewById(R.id.buttonEnableMicrophone);
        lightButton = rootView.findViewById(R.id.buttonEnableLight);

        // setup on click listeners
        micButton.setOnClickListener(v -> requestMicrophoneAccess());
        lightButton.setOnClickListener(v -> requestLuminanceAccess());

        // prime state with current permission + sensors
        boolean hasMicPermission = PermissionManager.hasPermission(requireContext(), Manifest.permission.RECORD_AUDIO);
        isMicPermissionDirty = hasMicPermission;
        viewModel.setHasRecordingPermission(hasMicPermission);

        boolean hasLightSensor = hasLightSensor();
        viewModel.getHasLuminanceSensor().setValue(hasLightSensor);
        if (!hasLightSensor) {
            lightButton.setEnabled(false);
            lightButton.setText(R.string.onboarding_sensors_light_unavailable);
        }

        // setup listeners for sensor availability / permissions
        viewModel.getHasRecordingPermission().observe(getViewLifecycleOwner(), hasPermission -> {
            // NOTE: if the user doesn't have permissions when the activity loads, show the default button
            if (!hasPermission && !isMicPermissionDirty) return;

            if (hasPermission) {
                int bg = resolveAttrColor(com.google.android.material.R.attr.colorTertiaryContainer);
                int fg = resolveAttrColor(com.google.android.material.R.attr.colorOnTertiaryContainer);

                micButton.setBackgroundTintList(ColorStateList.valueOf(bg));
                micButton.setIconTint(ColorStateList.valueOf(fg));
            } else {
                int disabledColor = resolveAttrColor(com.google.android.material.R.attr.colorErrorContainer);
                micButton.setBackgroundTintList(ColorStateList.valueOf(disabledColor));
            }
        });

        return rootView;
    }

    @Override
    public boolean validateStep() {
        Boolean hasMicPermission = viewModel.getHasRecordingPermission().getValue();
        if (hasMicPermission == null || !hasMicPermission) {
            Snackbar.make(rootView, R.string.onboarding_error_microphone_required, Snackbar.LENGTH_SHORT).show();
            requestMicrophoneAccess();
            return false;
        }

        Boolean hasLightSensor = viewModel.getHasLuminanceSensor().getValue();
        if (hasLightSensor != null && !hasLightSensor) {
            Snackbar.make(rootView, R.string.onboarding_sensors_light_unavailable, Snackbar.LENGTH_SHORT).show();
        }

        return true;
    }

    private int resolveAttrColor(int attr) {
        TypedValue tv = new TypedValue();
        requireContext().getTheme().resolveAttribute(attr, tv, true);
        return tv.data;
    }

    private void requestMicrophoneAccess() {
        System.out.println("Requesting microphone access");
        isMicPermissionDirty = true;
        micPermissionRequest.checkAndRequest(requireActivity());
    }

    private void requestLuminanceAccess() {
        // intentionally left blank until permissions are wired
    }

    private boolean hasLightSensor() {
        SensorManager sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager == null) return false;
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        return lightSensor != null;
    }
}
