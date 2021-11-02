package com.ekenya.rnd.offlinebanking.ui.login;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.ekenya.rnd.common.abstractions.BaseBottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class LoginDialogFragment extends BaseBottomSheetDialogFragment {
    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public Dialog getDialog() {
        return super.getDialog();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
