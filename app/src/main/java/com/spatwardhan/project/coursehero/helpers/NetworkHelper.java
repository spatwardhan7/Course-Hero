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

    // API Param keys
    private static final String API_Q_KEY = "q";
    private static final String API_Q_VAL = "search";
    private static final String API_QUERY_KEY = "query";
    private static final String API_START_KEY = "start";
    private static final String API_LIMIT_KEY = "limit";
    private static final String API_LIMIT_VAL = "20";
    private static final String API_FIELDS_KEY = "fields";
    //private static final String API_FIELDS_VAL = "courseId,onDemandSpecializationId,courses.v1(name,photoUrl,partnerIds,description),onDemandSpecializations.v1(name,logo,courseIds,partnerIds),partners.v1(name)";
    private static final String API_INCLUDES_KEY = "includes";
    //private static final String API_INCLUDES_VAL = "courseId,onDemandSpecializationId,courses.v1(partnerIds)";

    //

    private String index = "0";
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

    public void getCatalog(String searchText, final CustomCallback callback) {
        index = "0";
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

    public void loadMoreResults(final CustomCallback callback) {
        Request request = getCatalogRequest(searchQuery);
        enqueueCall(request, callback);
    }

    private void getNextIndex(JSONObject responseObject) {
        try {
            JSONObject pagingObject = responseObject.getJSONObject("paging");
            index = pagingObject.getString("next");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
            sb.append(URLEncoder.encode(string, "UTF-8").replace("+", "%20"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
