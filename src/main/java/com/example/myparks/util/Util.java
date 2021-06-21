package com.example.myparks.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {
    public static final String PARKS_URL = "https://developer.nps.gov/api/v1/parks?stateCode=az&api_key=wEoIeiEMrPpLoCQpcc2cpS6fGgSCUnjPxzIig3eH";

    public static String getParksUrl(String stateCode){
        return "https://developer.nps.gov/api/v1/parks?stateCode="+stateCode+"&api_key=wEoIeiEMrPpLoCQpcc2cpS6fGgSCUnjPxzIig3eH";
    }
    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
