package com.ekenya.rnd.baseapp.di.helpers.fragments;

// FragmentHelperKt.java

import androidx.fragment.app.Fragment;

public final class FragmentHelperKt {

    public static final Fragment newFragment(AddressableFragment addressableFragment) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //Intrinsics.checkNotNullParameter(addressableFragment, "addressableFragment");
        Object var10000 = Class.forName(addressableFragment.getClassName()).newInstance();
        if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type androidx.fragment.app.Fragment");
        } else {
            return (Fragment)var10000;
        }
    }
}
