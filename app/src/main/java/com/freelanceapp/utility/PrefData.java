package com.freelanceapp.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by y34h1a on 1/13/16.
 */
public class PrefData {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    public PrefData(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(Constants.PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setCurrentLanguage(String language){
        editor.putString(Constants.PREF_LANG, language);
        editor.commit();
    }
    public String getCurrentLanguage(){
        return pref.getString(Constants.PREF_LANG, "eng");
    }

}
