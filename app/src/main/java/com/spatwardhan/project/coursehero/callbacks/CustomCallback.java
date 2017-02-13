package com.spatwardhan.project.coursehero.callbacks;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by zky447 on 2/12/17.
 */

public interface CustomCallback {

    void onFailure(Call call, IOException e);

    void onResponse(Call call, JSONObject jsonObject) throws JSONException;
}
