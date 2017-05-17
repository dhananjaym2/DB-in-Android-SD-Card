package com.db_in_android_sd_card.util;

import android.content.Context;
import android.util.Log;

/**
 * Debug logging file for complete app.
 */

public class AppLog {

    /**
     * to turn off debugging/Log printing set the value of 'isLogEnabled' to false
     */
    private static boolean isLogEnabled = true;

    private static void printLog(char logType, String str_LogTag, String str_LogMessage) {

        if (isLogEnabled) {
            switch (logType) {

                case 'v':
                    Log.v(str_LogTag, str_LogMessage);
                    break;

                case 'd':
                    Log.d(str_LogTag, str_LogMessage);
                    break;

                case 'i':
                    Log.i(str_LogTag, str_LogMessage);
                    break;

                case 'w':
                    Log.w(str_LogTag, str_LogMessage);
                    break;

                case 'e':
                    Log.e(str_LogTag, str_LogMessage);
                    break;

                default:
                    Log.v(str_LogTag, str_LogMessage);
                    break;
            }
        }
    }

    /**
     * Log.v() equivalent - VERBOSE
     *
     * @param str_LogTag     tag
     * @param str_LogMessage message
     */
    public static void v(String str_LogTag, String str_LogMessage) {
        printLog('v', str_LogTag, str_LogMessage);
    }

    /**
     * Log.d() equivalent - DEBUG
     *
     * @param str_LogTag     tag
     * @param str_LogMessage message
     */
    public static void d(String str_LogTag, String str_LogMessage) {
        printLog('d', str_LogTag, str_LogMessage);
    }

    /**
     * Log.i() equivalent - INFO
     *
     * @param str_LogTag     tag
     * @param str_LogMessage message
     */
    public static void i(String str_LogTag, String str_LogMessage) {
        printLog('i', str_LogTag, str_LogMessage);
    }

    /**
     * Log.w() equivalent - WARN
     *
     * @param str_LogTag     tag
     * @param str_LogMessage message
     */
    public static void w(String str_LogTag, String str_LogMessage) {
        printLog('w', str_LogTag, str_LogMessage);
    }

    /**
     * Log.e() equivalent - ERROR
     *
     * @param str_LogTag     tag
     * @param str_LogMessage message
     */
    public static void e(String str_LogTag, String str_LogMessage) {
        printLog('e', str_LogTag, str_LogMessage);
    }

    /**
     * show error message in the log and also show Toast
     *
     * @param context       context
     * @param strMethodName String MethodName where Exception occurred
     * @param exception     exception
     */
    public static void showErrorToastAndLog(Context context, String strMethodName, Exception
            exception) {

        if (isLogEnabled) {
            String strExceptionMessage = "" + strMethodName + "() exception:" + exception;

            printLog('e', "AppLog_Exception", strExceptionMessage);
            exception.printStackTrace();

            AppUtil.showToast(context, strExceptionMessage, true);
        }
    }
}