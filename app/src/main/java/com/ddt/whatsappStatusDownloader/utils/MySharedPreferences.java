package com.ddt.whatsappStatusDownloader.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static MySharedPreferences mySharedPreferences;


    private MySharedPreferences(Context context) {

        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static MySharedPreferences getInstance(Context context) {

        if (sharedPreferences == null)
            mySharedPreferences = new MySharedPreferences(context);

        return mySharedPreferences;
    }


    public int getNumberOfOpenApp() {
        return sharedPreferences.getInt(Constants.NUMBER_OF_OPEN_APP, 0);
    }

    public void setNumberOfOpenApp(int numberOfOpenApp) {
        editor.putInt(Constants.NUMBER_OF_OPEN_APP, numberOfOpenApp).apply();
    }

    public void setIsCommentRegister(boolean isDarkMode) {
        editor.putBoolean(Constants.IS_REGISTER_COMMENT, isDarkMode).apply();
    }

    public boolean isCommentRegister() {
        return sharedPreferences.getBoolean(Constants.IS_REGISTER_COMMENT, false);
    }



}

