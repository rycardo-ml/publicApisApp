package com.example.apis.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.apis.R
import com.google.android.material.bottomnavigation.BottomNavigationView

// https://medium.com/koin-developers/ready-for-koin-2-0-2722ab59cac3
// https://medium.com/@harmittaa/setting-up-koin-2-0-1-for-android-ebf11de01816

// https://medium.com/@manuaravindpta/networking-using-volley-library-39c22061b4ba

// https://developer.android.com/guide/navigation/navigation-nested-graphs

// http://fabcoding.com/design-an-easy-simple-elegant-onboarding-screen-using-recyclerview/

// https://github.com/public-apis/public-apis

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpNavigation()
    }

    private fun setUpNavigation() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bttm_nav)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        NavigationUI.setupWithNavController(
            bottomNavigationView,
            navHostFragment!!.navController
        )

        //OPEN IN THIS MENU
        //bottomNavigationView.selectedItemId = R.id.nav_main_settings
    }
}

/*
 * HANDLING MENU MANUAL
 * https://developer.android.com/guide/navigation/navigation-animate-transitions
 * https://github.com/android/architecture-components-samples/issues/537
 * https://stackoverflow.com/questions/54344787/adding-custom-transition-animations-to-bottom-navigation-setup-with-jetpack-navi

    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

    NavOptions navOptions = new NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_right)
            .setPopExitAnim(R.anim.slide_out_left)
            .setPopUpTo(navController.getGraph().getStartDestination(), false)
            .build();

    bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            boolean handled = false;
            if (navController.getGraph().findNode(item.getItemId()) != null) {
                navController.navigate(item.getItemId(), null, navOptions);
                handled = true;
            } else {
                switch (item.getItemId()) {
                    case R.id.settings:
                        openSettingsActivity();
                        break;
                }
            }

            return handled;
        }
    });
*/