package ch.inf.usi.mindbricks.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

import ch.inf.usi.mindbricks.MainActivity;
import ch.inf.usi.mindbricks.R;
import ch.inf.usi.mindbricks.util.PreferencesManager;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private MaterialButton buttonSkip;
    private MaterialButton buttonNext;
    private PreferencesManager prefs;
    private OnboardingPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        prefs = new PreferencesManager(this);

        viewPager = findViewById(R.id.viewPagerOnboarding);
        buttonSkip = findViewById(R.id.buttonSkip);
        buttonNext = findViewById(R.id.buttonNext);

        pagerAdapter = new OnboardingPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        buttonSkip.setOnClickListener(v -> finishOnboarding());

        buttonNext.setOnClickListener(v -> {
            int pos = viewPager.getCurrentItem();
            if (pos < pagerAdapter.getItemCount() - 1) {
                viewPager.setCurrentItem(pos + 1);
            } else {
                finishOnboarding();
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateBottomButtons(position);
            }
        });

        updateBottomButtons(viewPager.getCurrentItem());
    }

    private void updateBottomButtons(int position) {
        boolean isLastPage = position == pagerAdapter.getItemCount() - 1;
        buttonNext.setText(isLastPage ? R.string.onboarding_start : R.string.onboarding_next);
        buttonSkip.setVisibility(isLastPage ? View.GONE : View.VISIBLE);
    }

    private void finishOnboarding() {
        // toggle flag + go to main activity
        prefs.setOnboardingComplete();

        // you probably want to also save user data before this from page 1
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
