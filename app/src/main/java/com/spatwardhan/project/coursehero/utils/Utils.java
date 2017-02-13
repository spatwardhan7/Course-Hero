package com.spatwardhan.project.coursehero.utils;

import org.json.JSONArray;

/**
 * Created by zky447 on 2/12/17.
 */

public class Utils {
    public static boolean isNullOrEmpty(JSONArray jsonArray) {
        return (jsonArray == null || jsonArray.length() == 0);
    }
}
