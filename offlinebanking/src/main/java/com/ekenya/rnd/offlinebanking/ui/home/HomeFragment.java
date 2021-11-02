package com.ekenya.rnd.offlinebanking.ui.home;

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

import com.ekenya.rnd.common.abstractions.BaseActivity;
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment;
import com.ekenya.rnd.ethdroid.ChainConfig;
import com.ekenya.rnd.ethdroid.EthDroid;
import com.ekenya.rnd.ethdroid.model.Transaction;
import com.ekenya.rnd.offlinebanking.R;
import com.ekenya.rnd.offlinebanking.databinding.FragmentHomeBinding;
import com.ekenya.rnd.offlinebanking.ethereum.contracts.IContractSample;
import com.ekenya.rnd.offlinebanking.ui.dashboard.DashboardFragment;
import com.google.android.material.snackbar.Snackbar;

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

public class HomeFragment extends BaseDaggerFragment {
    private final String TAG = HomeFragment.class.getSimpleName();

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private static final int STORAGE_REQUEST_CODE = 0x15;
    private EthDroid mEthDroid;

    private String mDataDir;

    private IContractSample mContractInstance;
    private static final String CONTRACT_ADDRESS = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataDir = requireActivity().getFilesDir().getAbsolutePath()+"/GethDroid";

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(getActivity(),viewModelFactory).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startNode();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                    startNode();
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                    Snackbar bar = Snackbar.make(
                            binding.getRoot(),
                            "Write Storage permissions a required ",
                            Snackbar.LENGTH_INDEFINITE
                    ).setAction("Allow", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requestPermissions(
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    STORAGE_REQUEST_CODE
                            );
                        }
                    });
                    bar.show();
                }
            default:
                //
                break;
        }
    }

    /**
     * Start the sample node
     */
    private  void startNode(){
        homeViewModel.getText().setValue("Starting Geth Node ..");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if(ActivityCompat
                    .checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is not granted");
                //
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_REQUEST_CODE
                );
                return;
            }else if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), WRITE_EXTERNAL_STORAGE)){
                Snackbar bar = Snackbar.make(
                        binding.getRoot(),
                        "Write Storage permissions a required ",
                        Snackbar.LENGTH_INDEFINITE
                ).setAction("Allow", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestPermissions(
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                STORAGE_REQUEST_CODE
                        );
                    }
                });
                bar.show();
            }
        }

        //
        deleteDirIfExists(new File(mDataDir));
        InputStream is  = getResources().openRawResource(R.raw.genesis_json);
        Writer writer  = new StringWriter();
        char[] buffer = new char[1024];
        try {
            java.io.Reader reader  = new java.io.BufferedReader(new InputStreamReader( is,"UTF-8" ));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

            //
            String genesis  = writer.toString();
            //
            Long networkID = 1000L;

            //Private Network Configuration
            String modeID = "a448517e9e7c6ae984c040791573b7e7b383461e34f546136e5e8ee8c3a4a61f8ee8f6836cb35a0b9e7de88bfaf5f01e528639a61357ab81f5fa8c5bc5e6a412";
            String nodeIP = "10.33.44.111";
            String nodePort = "30301";
            String bootnode = "enode://${modeID}@${nodeIP}:${nodePort}?discport:${nodePort}+1";
            //
            ChainConfig config = new ChainConfig.Builder(networkID, genesis, bootnode).build();
            //
            EthDroid ethdroid = new EthDroid.Builder(mDataDir)
                    .withDefaultContext()
                    .withChainConfig(config)
                    .build();
            //
            ethdroid.start();
            //
            mEthDroid = ethdroid;
            //
            if(checkNodeBuilt()){
                //
                IContractSample it = mEthDroid.getContractInstance(IContractSample.class, CONTRACT_ADDRESS);
                if(it != null){
                    mContractInstance = it;
                    //
                    getContractBalance();
                }
            }

        }catch (Exception e ){
            Log.e(TAG, "Exception thrown : " + e.getMessage());
            homeViewModel.getText().postValue("Start Node Failed\n"+e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Simple test to check if the node is up and running
     * @return
     */
    private Boolean checkNodeBuilt() {
        //
        if(mEthDroid == null){
            //Failed ...
            return false;
        }

        //
        File dir  = new File(mDataDir.toString());
        if(!dir.exists()){
            //Failed ...
            return false;
        }
        //
        if(!dir.isDirectory()){
            //Failed ...
            return false;
        }
        //
        if(dir.list().length != 3){
            //Failed ...
            return false;
        }
        //
        for (String fileName : dir.list()) {
            if(
                    fileName.compareTo("LOCK") == 0 ||
                            fileName.compareTo("nodekey") == 0 ||
                            fileName.compareTo("lightchaindata")!= 0) {
                //Failed ...
                return false;
            }
        }
        //Success
        return true;
    }

    private void getContractBalance(){

        if(mContractInstance == null){
            return;
        }
        //Execute a transaction in the blockchain
        //
        try {
            //
            mContractInstance.getFloatBalance().sendWithNotification()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer() {
                        @Override
                        public void accept(Object o) throws Exception {

                        }
                    });
            //
            Transaction transaction = mContractInstance.getFloatBalance().getTransaction();
            transaction.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        homeViewModel.getText().setValue("Geth Node Started Successfully ");
    }
}