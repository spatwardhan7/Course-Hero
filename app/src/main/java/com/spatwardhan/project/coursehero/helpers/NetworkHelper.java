package com.spatwardhan.project.coursehero.helpers;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zky447 on 2/11/17.
 */

public class NetworkHelper {
    // Singleton members
    private static NetworkHelper instance = null;
    private static OkHttpClient client = null;

    // API Param keys
    private static final String API_Q_KEY = "q";
    private static final String API_Q_VAL = "search";
    private static final String API_QUERY_KEY = "query";
    private static final String API_QUERY_VAL = "machine%20learning";
    private static final String API_START_KEY = "start";
    private static final String API_START_VAL = "0";
    private static final String API_LIMIT_KEY = "limit";
    private static final String API_LIMIT_VAL = "20";
    private static final String API_FIELDS_KEY = "fields";
    private static final String API_FIELDS_VAL = "courseId,onDemandSpecializationId,courses.v1(name,photoUrl,partnerIds,description),onDemandSpecializations.v1(name,logo,courseIds,partnerIds),partners.v1(name)";
    private static final String API_INCLUDES_KEY = "includes";
    private static final String API_INCLUDES_VAL = "courseId,onDemandSpecializationId,courses.v1(partnerIds)";

    // Page count variable
    private int page = 1;

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

    public void getCatalog(String searchText, final Callback callback) {
        Request request = getCatalogRequest(searchText);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }

    private Request getCatalogRequest(String searchText) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(ApiEndpointHelper.apiEndpoint + ApiEndpointHelper.API_GET_CATALOG_RESULTS).newBuilder();
        urlBuilder.addQueryParameter(API_Q_KEY, API_Q_VAL);
        urlBuilder.addQueryParameter(API_QUERY_KEY, searchText);
        urlBuilder.addQueryParameter(API_START_KEY, API_START_VAL);
        urlBuilder.addQueryParameter(API_LIMIT_KEY, API_LIMIT_VAL);
        urlBuilder.addQueryParameter(API_FIELDS_KEY, API_FIELDS_VAL);
        urlBuilder.addQueryParameter(API_INCLUDES_KEY, API_INCLUDES_VAL);
        String url = urlBuilder.build().toString();

        return new Request.Builder()
                .url(url)
                .build();
    }
}
