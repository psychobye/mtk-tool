package com.psych.mtktool;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/* loaded from: classes.dex */
public class a {
    public static void a(Context context) {
        context.getApplicationContext().getSharedPreferences("app_config", 0).edit().putBoolean("showAlert", false).apply();
    }

    private static int b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int c(Context context) {
        return context.getApplicationContext().getSharedPreferences("app_config", 0).getInt("firstAppRun", 0);
    }

    public static boolean d(Context context) {
        int c2 = c(context);
        int b2 = b(context);
        f(context, b2);
        return (c2 == 0 || b2 == c2) ? false : true;
    }

    public static boolean e(Context context) {
        return context.getApplicationContext().getSharedPreferences("app_config", 0).getBoolean("showAlert", Build.VERSION.SDK_INT >= 30);
    }

    private static void f(Context context, int i) {
        context.getApplicationContext().getSharedPreferences("app_config", 0).edit().putInt("firstAppRun", i).apply();
    }
}
