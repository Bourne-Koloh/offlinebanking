package com.ekenya.rnd.offlinebanking.ui.dashboard;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static com.ekenya.rnd.ethdroid.Utils.deleteDirIfExists;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ekenya.rnd.common.abstractions.BaseDaggerFragment;
import com.ekenya.rnd.ethdroid.ChainConfig;
import com.ekenya.rnd.ethdroid.EthDroid;
import com.ekenya.rnd.ethdroid.model.Transaction;
import com.ekenya.rnd.offlinebanking.R;
import com.ekenya.rnd.offlinebanking.databinding.FragmentDashboardBinding;
import com.ekenya.rnd.offlinebanking.ethereum.contracts.IContractSample;
import com.google.android.material.snackbar.Snackbar;

import org.ethereum.geth.Hash;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class DashboardFragment extends BaseDaggerFragment {
    private final String TAG = DashboardFragment.class.getSimpleName();
    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this,viewModelFactory).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}