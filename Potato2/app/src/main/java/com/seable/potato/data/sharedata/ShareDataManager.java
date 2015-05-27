package com.seable.potato.data.sharedata;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by yaohu on 15/5/11.
 */
public class ShareDataManager {

    private SharedPreferences sharedPreferences;

    private final String UASERNAME = "userName";
    private final String PWD = "pwd";

    public ShareDataManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public void saveSharedUserInfo(String userName, String pwd, String id) {
        sharedPreferences.edit().putString(UASERNAME, userName).putString(PWD, pwd).putString("id", id).commit();

    }

    public String getId() {
        return sharedPreferences.getString("id", "");
    }


    public String[] getUserNamePsw(){
        String[] values=new String[2];
        values[0]=sharedPreferences.getString(UASERNAME,"");
        values[1]=sharedPreferences.getString(PWD,"");
        return values;
    }


}
