package com.ekenya.rnd.common.abstractions;

import android.os.Bundle;

import androidx.annotation.Nullable;

import dagger.android.support.DaggerFragment;

public abstract class BaseDaggerFragment extends DaggerFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
