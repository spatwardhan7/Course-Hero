package com.spatwardhan.project.coursehero.activities;

import android.app.Activity;
import android.os.Bundle;

import com.spatwardhan.project.coursehero.R;
import com.spatwardhan.project.coursehero.helpers.NetworkHelper;
import com.spatwardhan.project.coursehero.models.CatalogElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends Activity {

    private NetworkHelper networkHelper;

    private List<CatalogElement> catalogElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkHelper = NetworkHelper.getInstance();

        catalogElements = new ArrayList<>();

        getCatalog();
    }

    private void getCatalog() {
        networkHelper.getCatalog(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject((response.body().string()));
                    if (jsonObject.length() > 0) {
                        catalogElements.addAll(CatalogElement.parseJsonResponse(jsonObject));
                        System.out.println(catalogElements.size());
                    }
                } catch (JSONException e) {

                }
            }
        });
    }
}
