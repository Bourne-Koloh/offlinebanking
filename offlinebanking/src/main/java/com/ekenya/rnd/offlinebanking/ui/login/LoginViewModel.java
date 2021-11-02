package com.ekenya.rnd.offlinebanking.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class LoginViewModel extends AndroidViewModel {

    @Inject
    public LoginViewModel(@NonNull @NotNull Application application) {
        super(application);
    }
}
