package com.dg.info.civildesk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.dg.info.civildesk.models.UserData;
import com.google.gson.Gson;


public class LocalData {
    private static LocalData instance;

    private static Context appContext;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public LocalData() {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        editor = preferences.edit();
        gson = new Gson();
    }

    public static synchronized LocalData getInstance(Context context) {
        appContext = context;
        if (instance == null) {
            instance = new LocalData();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        boolean isLoggedIn = preferences.getBoolean(Constants.IS_LOGGED_IN, false);
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(Constants.IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }


    public UserData getSignIn() {
        String signInResponse = preferences.getString(Constants.SIGN_IN_DATA, "");
        if (!TextUtils.isEmpty(signInResponse)) {
            UserData signIn = gson.fromJson(signInResponse, UserData.class);
            return signIn;
        } else
            return null;
    }

    public void setSignIn(UserData userData) {
        editor.putString(Constants.SIGN_IN_DATA, gson.toJson(userData));
        editor.commit();
    }


    public void setMobileNumber(String mobileNumber) {
        editor.putString(Constants.MOBILE_NO, mobileNumber);
        editor.commit();
    }

    public boolean isMobileVerified() {
        boolean isMobileVerified = preferences.getBoolean(Constants.IS_MOBILE_VERIFIED, false);
        return isMobileVerified;
    }

    public void setMobileVerified(boolean isMobileVerified) {
        editor.putBoolean(Constants.IS_MOBILE_VERIFIED, isMobileVerified);
        editor.commit();
    }


    public String getUserId() {
        String userId = preferences.getString(Constants.USER_ID, "");
        if (!TextUtils.isEmpty(userId)) {
            return userId;
        } else
            return null;
    }

    public void setUserId(String userId) {
        editor.putString(Constants.USER_ID, userId);
        editor.commit();
    }

    public String getNewUserId() {
        String newuUerId = preferences.getString(Constants.NEW_USER_ID, "");
        if (!TextUtils.isEmpty(newuUerId)) {
            return newuUerId;
        } else
            return null;
    }

    public void setNewUserId(String newuUerId) {
        editor.putString(Constants.NEW_USER_ID, newuUerId);
        editor.commit();
    }

    public String getNewUserName() {
        String newuUerName = preferences.getString(Constants.NEW_USER_Name, "");
        if (!TextUtils.isEmpty(newuUerName)) {
            return newuUerName;
        } else
            return null;
    }

    public void setNewUserName(String newuUerName) {
        editor.putString(Constants.NEW_USER_Name, newuUerName);
        editor.commit();
    }

    public String getAssignCompanyId() {
        String coId = preferences.getString(Constants.CO_ASSIGN_DATA, "");
        if (!TextUtils.isEmpty(coId)) {
            return coId;
        } else
            return null;
    }

    public void setAssignComanyId(String coId) {
        editor.putString(Constants.CO_ASSIGN_DATA, coId);
        editor.commit();
    }

    public String getAssignCompanyName() {
        String coName = preferences.getString(Constants.CO_ASSIGN_NAME, "");
        if (!TextUtils.isEmpty(coName)) {
            return coName;
        } else
            return null;
    }

    public void setAssignComanyName(String coName) {
        editor.putString(Constants.CO_ASSIGN_NAME, coName);
        editor.commit();
    }
    public String getAssignCompanyAddress() {
        String Address = preferences.getString(Constants.CO_ASSIGN_ADDRESS, "");
        if (!TextUtils.isEmpty(Address)) {
            return Address;
        } else
            return null;
    }

    public void setAssignComanyAddress(String Address) {
        editor.putString(Constants.CO_ASSIGN_ADDRESS, Address);
        editor.commit();
    }

    public String getUserRole() {
        String Role = preferences.getString(Constants.USER_Role, "");
        if (!TextUtils.isEmpty(Role)) {
            return Role;
        } else
            return null;
    }

    public void setUserRole(String Role) {
        editor.putString(Constants.USER_Role, Role);
        editor.commit();
    }

    public String getUserSubActGroupID() {
        String SubActGroupID = preferences.getString(Constants.SubActGroup_ID, "");
        if (!TextUtils.isEmpty(SubActGroupID)) {
            return SubActGroupID;
        } else
            return null;
    }

    public void setUserSubActGroupID(String SubActGroupID) {
        editor.putString(Constants.SubActGroup_ID, SubActGroupID);
        editor.commit();
    }
    public void logout() {
        editor.clear();
        editor.commit();
    }

}
