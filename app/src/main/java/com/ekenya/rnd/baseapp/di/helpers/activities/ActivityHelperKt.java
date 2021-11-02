package com.ekenya.rnd.baseapp.di.helpers.activities;

import android.content.Context;
import android.content.Intent;


public final class ActivityHelperKt {

    public static final Intent intentTo(Context context, AddressableActivity addressableActivity) {

//        Intent var10000 = (new Intent("android.intent.action.VIEW"))
//                .setClassName(Constants.BASE_PACKAGE_NAME+context.getPackageName(), addressableActivity.getClassName());

        Intent var10000 = (new Intent("android.intent.action.VIEW"))
                .setClassName(context.getPackageName(), addressableActivity.getClassName());

        return var10000;
    }
}
