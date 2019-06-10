package com.example.gnbtraders.utils;

import android.content.Context;
import android.widget.Toast;


public class Utils {


    private void Utils() {}


    public static Toast showToastMessage(Context context, Toast toast, String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        return toast;
    }

    public static boolean isEmptyString(String stringToCheck) {
        return (stringToCheck == null || stringToCheck.trim().length() == 0);
    }

}
