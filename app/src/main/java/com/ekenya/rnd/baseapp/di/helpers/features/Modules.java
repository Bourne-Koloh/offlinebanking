package com.ekenya.rnd.baseapp.di.helpers.features;

import com.ekenya.rnd.baseapp.di.helpers.activities.AddressableActivity;
import com.ekenya.rnd.common.Constants;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class Modules {


    private static final List<FeatureModule> modules;

    public static final Modules INSTANCE;

    public final FeatureModule getModuleFromName(String moduleName) {

        Iterator var4 = modules.iterator();

        FeatureModule it;
        do {
            if (!var4.hasNext()) {
                throw new IllegalArgumentException(moduleName + " is not found");
            }
            Object element$iv = var4.next();
            it = (FeatureModule) element$iv;
        } while (!it.getName().equalsIgnoreCase(moduleName));

        return it;
    }

    private Modules() {
    }

    static {
        Modules var0 = new Modules();
        INSTANCE = var0;
        modules = Arrays.asList(new FeatureModule[]{(FeatureModule) FeatureOfflineBanking.INSTANCE,
                (FeatureModule) FeatureSupport.INSTANCE}.clone()
        );
    }

    public static final class FeatureOfflineBanking implements FeatureModule, AddressableActivity {

        private static final String name;

        private static final String injectorName;

        private static final String className;

        public static final FeatureOfflineBanking INSTANCE;

        public String getName() {
            return name;
        }

        public String getInjectorName() {
            return injectorName;
        }

        private FeatureOfflineBanking() {
        }

        static {
            FeatureOfflineBanking var0 = new FeatureOfflineBanking();
            INSTANCE = var0;
            name = "offlinebanking";
            injectorName = Constants.BASE_PACKAGE_NAME+"."+name+".di.OfflineBankingInjector";
            className = Constants.BASE_PACKAGE_NAME +"."+name+".MainActivity";
        }

        @Override
        public String getClassName() {
            return className;
        }
    }

    public static final class FeatureSupport implements FeatureModule {

        private static final String name;

        private static final String injectorName;

        public static final FeatureSupport INSTANCE;

        public String getName() {
            return name;
        }

        public String getInjectorName() {
            return injectorName;
        }

        private FeatureSupport() {
        }

        static {
            FeatureSupport var0 = new FeatureSupport();
            INSTANCE = var0;
            name = "support";
            injectorName = Constants.BASE_PACKAGE_NAME+".support.di.SupportInjector";
        }
    }

}
