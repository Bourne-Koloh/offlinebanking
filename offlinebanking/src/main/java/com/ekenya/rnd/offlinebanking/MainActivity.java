package com.ekenya.rnd.offlinebanking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ekenya.rnd.baseapp.BaseApp;
import com.ekenya.rnd.common.abstractions.BaseActivity;
import com.ekenya.rnd.offlinebanking.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import dagger.android.AndroidInjector;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    //private MainViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //
        //mViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        getSupportActionBar().setTitle(com.ekenya.rnd.baseapp.R.string.title_offline_banking);
    }
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector()  {
        // Fragment Injector should use the Application class
        // If necessary, I will use AndroidInjector as well as App class (I have not done this time)
        return ((BaseApp)getApplication()).supportFragmentInjector();
    }

}