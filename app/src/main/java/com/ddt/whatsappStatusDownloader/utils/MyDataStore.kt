package com.ddt.whatsappStatusDownloader.utils

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

class MyDataStore(context: Context) {
    private val Context.dataStore by preferencesDataStore("datastore")


    private val NUMBER_OF_OPEN_APP_KEY = intPreferencesKey("NUMBER_OF_OPEN_APP_KEY")
    private val IS_REGISTER_COMMENT_KEY = booleanPreferencesKey("IS_REGISTER_COMMENT")


}