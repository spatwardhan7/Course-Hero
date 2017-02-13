package com.spatwardhan.project.coursehero.helpers;

import com.spatwardhan.project.coursehero.callbacks.CustomCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkHelper {
    // Singleton members
    private static NetworkHelper instance = null;
    private static OkHttpClient client = null;

    // API Params
    private static final String API_Q_KEY = "q";
    private static final String API_Q_VAL = "search";
    private static final String API_QUERY_KEY = "query";
    private static final String API_START_KEY = "start";
    private static final String API_LIMIT_KEY = "limit";
    private static final String API_LIMIT_VAL = "20";
    private static final String API_FIELDS_KEY = "fields";
    private static final String API_INCLUDES_KEY = "includes";

    // Helper Strings
    private static final String ZERO = "0";
    private static final String PAGING = "paging";
    private static final String NEXT = "next";
    private static final String ENCODING = "UTF-8";
    private static final String PLUS = "+";
    private static final String PERCENT_20 = "%20";

    // Index and query for pagination
    private String index = ZERO;
    private String searchQuery;

    public static NetworkHelper getInstance() {
        // Non-Thread Safe Singleton implementation
        // since thread safety decreases performance
        // and is not required in this current app.
        if (instance == null) {
            instance = new NetworkHelper();
            client = new OkHttpClient();
        }
        return instance;
    }

    // Private Constructor to prevent instantiation from outside this class
    private NetworkHelper() {
    }

    // Get Catalog is called when the users clicks on the Search Button
    public void getCatalog(String searchText, final CustomCallback callback) {
        // Since this is a new call, set index to 0
        // and save user's search text, both of these fields
        // will be used later for pagination
        index = ZERO;
        searchQuery = searchText;
        Request request = getCatalogRequest(searchText);
        enqueueCall(request, callback);
    }

    private void enqueueCall(Request request, final CustomCallback callback) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject((response.body().string()));
                        getNextIndex(jsonObject);
                        callback.onResponse(call, jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }

    // Called when user runs out of current results by scrolling the list
    // Use current search query and next index to get next set of results
    public void loadMoreResults(final CustomCallback callback) {
        Request request = getCatalogRequest(searchQuery);
        enqueueCall(request, callback);
    }

    // Parse response object to get next index of results
    private void getNextIndex(JSONObject responseObject) {
        try {
            JSONObject pagingObject = responseObject.getJSONObject(PAGING);
            index = pagingObject.getString(NEXT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Build request using Endpoint and params
    private Request getCatalogRequest(String searchText) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(ApiEndpointHelper.apiEndpoint + ApiEndpointHelper.API_GET_CATALOG_RESULTS).newBuilder();
        urlBuilder.addQueryParameter(API_Q_KEY, API_Q_VAL);
        urlBuilder.addQueryParameter(API_QUERY_KEY, encodeString(searchText));
        urlBuilder.addQueryParameter(API_START_KEY, index);
        urlBuilder.addQueryParameter(API_LIMIT_KEY, API_LIMIT_VAL);
        urlBuilder.addQueryParameter(API_FIELDS_KEY, ApiEndpointHelper.API_FIELDS);
        urlBuilder.addQueryParameter(API_INCLUDES_KEY, ApiEndpointHelper.API_INCLUDES);
        String url = urlBuilder.build().toString();

        return new Request.Builder()
                .url(url)
                .build();
    }

    private String encodeString(String string) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(URLEncoder.encode(string, ENCODING).replace(PLUS, PERCENT_20));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
