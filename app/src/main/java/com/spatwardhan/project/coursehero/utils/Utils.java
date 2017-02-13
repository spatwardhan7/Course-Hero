package com.spatwardhan.project.coursehero.utils;

import org.json.JSONArray;

public class Utils {
    public static boolean isNullOrEmpty(JSONArray jsonArray) {
        return (jsonArray == null || jsonArray.length() == 0);
    }
}
