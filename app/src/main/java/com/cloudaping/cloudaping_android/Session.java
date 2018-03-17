package com.cloudaping.cloudaping_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by reggie on 17/03/18.
 */

public class Session {

    private SharedPreferences prefs;
    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }
    public void remove(String key){
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key);
        editor.apply();
    }
    public void setCustomerID(String customerID) {
        prefs.edit().putString("customerID", customerID).commit();
    }

    public String getCustomerID() {
        String customerID = prefs.getString("customerID","");
        return customerID;
    }
}