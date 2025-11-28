package ch.inf.usi.mindbricks;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ch.inf.usi.mindbricks.databinding.ActivityMainBinding;
import ch.inf.usi.mindbricks.ui.analytics.AnalyticsActivity;
import ch.inf.usi.mindbricks.ui.nav.NavigationLocker; // Corrected import path from previous examples

public class MainActivity extends AppCompatActivity implements NavigationLocker {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_shop, R.id.navigation_profile, R.id.navigation_analytics)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // This line will still handle the fragment destinations (Home, Shop, Profile)
        NavigationUI.setupWithNavController(binding.navView, navController);

        // --- ADDED: Custom listener to handle the special AnalyticsActivity case ---
        binding.navView.setOnItemSelectedListener(item -> {
            // Check if the selected item is the one for the AnalyticsActivity
            if (item.getItemId() == R.id.navigation_analytics) {
                // If so, launch the activity manually
                Intent intent = new Intent(this, AnalyticsActivity.class);
                startActivity(intent);
                // Return false to prevent the item from being selected in the bottom nav bar.
                // This is important because we are leaving the current screen.
                return false;
            }
            // For all other items (Home, Shop, Profile), let the Navigation Component handle it.
            // onNavDestinationSelected will automatically navigate to the correct fragment.
            return NavigationUI.onNavDestinationSelected(item, navController);
        });
    }

    @Override
    public void setNavigationEnabled(boolean enabled) {
        if (binding != null && binding.navView != null) {
            for (int i = 0; i < binding.navView.getMenu().size(); i++) {
                binding.navView.getMenu().getItem(i).setEnabled(enabled);
            }
        }
    }
}
