package com.ekenya.rnd.common.repo;

import android.content.Context;

public final class SampleDataRepo implements SampleRepository {
    private final Context context;


    public String getData() {
        return "Package name: " + this.context.getPackageName() + ", currentTimeMillis: " + System.currentTimeMillis();
    }

    public SampleDataRepo(Context context) {
        super();
        this.context = context;
    }
}
