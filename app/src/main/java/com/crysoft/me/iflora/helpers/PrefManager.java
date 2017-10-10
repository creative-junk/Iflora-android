package com.crysoft.me.iflora.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Maxx on 10/9/2017.
 */

public class PrefManager {
 SharedPreferences pref;
 SharedPreferences tokenPref;
 SharedPreferences.Editor editor;
 Context _context;

 int PRIVATE_MODE=0;

 private static final String PREF_NAME = "iflora-welcome";
 private static final String IS_FIRST_TIME_LAUNCH = "";
 private static final String TOKEN_NAME = "iflora-token";

 public PrefManager(Context context){
     this._context =context;
     pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
     tokenPref = _context.getSharedPreferences(TOKEN_NAME,PRIVATE_MODE);
     editor = pref.edit();
 }
 public void setIsFirstTimeLaunch(boolean isFirstTimeLaunch){
     editor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTimeLaunch);
     editor.commit();
 }
 public boolean isFirstTimeLaunch(){
     return pref.getBoolean(IS_FIRST_TIME_LAUNCH,true);
 }
 public void setToken(String token){
     editor.putString(TOKEN_NAME,token);
     editor.commit();
 }
 public String getToken(){
     return tokenPref.getString(TOKEN_NAME,null);
 }
}
