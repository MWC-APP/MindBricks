package ch.inf.usi.mindbricks.ui.nav.home;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import java.util.Locale;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import ch.inf.usi.mindbricks.R;


public class HomeFragment extends Fragment {

    // Declare UI views and state variables
    private TextView timerTextView;
    private Button startStopButton;
    private Button resetButton;

    private int seconds = 0;
    private boolean isRunning = false;
    private Handler timerHandler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;

    //  Connect the fragment to the "fragment_timer.xml" layout file
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the views from the layout with IDs
        timerTextView = view.findViewById(R.id.timer_text_view);
        startStopButton = view.findViewById(R.id.start_stop_button);
        resetButton = view.findViewById(R.id.reset_button);

        // define button functipns
        startStopButton.setOnClickListener(v -> handleStartStop());
        resetButton.setOnClickListener(v -> handleReset());
    }

    private void handleStartStop() {
        if (isRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    private void handleReset() {
        stopTimer();
        seconds = 0;
        updateTimerUI(); // Update the display to show "00:00:00"
    }

    private void startTimer() {
        isRunning = true;
        startStopButton.setText("Stop"); // Change button text

        // Create the action that will run every second
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                seconds++;      // Increment the counter
                updateTimerUI(); // Update the screen
                timerHandler.postDelayed(this, 1000); // Schedule this to run again in 1 second
            }
        };

        // Start the timer immediately
        timerHandler.post(timerRunnable);
    }

    private void stopTimer() {
        isRunning = false;
        startStopButton.setText("Start"); // Change button text back
        timerHandler.removeCallbacks(timerRunnable); // Stop the runnable
    }

    private void updateTimerUI() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        String timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
        timerTextView.setText(timeString);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timerHandler.removeCallbacks(timerRunnable);
    }
}
