package com.spatwardhan.project.coursehero.callbacks;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;


/*
 *  Callback provided by okhttp has an issue where response.body().string() can be called
 *  only once. I had to parse the response two times, once for pagination info and then for
 *  retrieving catalog elements. With this callback, I create a JSONObject immediately
 *  after receiving the response and pass that around instead of Response.
 */
public interface CustomCallback {

    void onFailure(Call call, IOException e);

    void onResponse(Call call, JSONObject jsonObject) throws JSONException;
}
