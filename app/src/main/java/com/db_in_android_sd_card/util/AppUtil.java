package com.db_in_android_sd_card.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Utilities class containing generic methods available to be used by complete app.
 */

public class AppUtil {
    private static final String LOG_TAG = AppUtil.class.getSimpleName();

    public static void showToast(Context context, String messageToShowOnToast, boolean isToastShownForLong) {
        if (context != null) {
            if (isToastShownForLong)
                Toast.makeText(context, messageToShowOnToast, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, messageToShowOnToast, Toast.LENGTH_SHORT).show();
        } else {
            AppLog.e(LOG_TAG, "context is null");
        }
    }
}