package com.spatwardhan.project.coursehero.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.spatwardhan.project.coursehero.R;
import com.spatwardhan.project.coursehero.adapters.CatalogAdapter;
import com.spatwardhan.project.coursehero.callbacks.CustomCallback;
import com.spatwardhan.project.coursehero.helpers.NetworkHelper;
import com.spatwardhan.project.coursehero.listeners.EndlessScrollListener;
import com.spatwardhan.project.coursehero.models.CatalogElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.Call;

public class CatalogSearchActivity extends Activity {
    // Layout Views
    @BindView(R.id.searchEditText)
    EditText searchEditText;

    @BindView(R.id.searchButton)
    Button searchButton;

    @BindView(R.id.listView)
    ListView listView;

    private NetworkHelper networkHelper;
    private List<CatalogElement> catalogElements;
    private CatalogAdapter catalogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_search);
        ButterKnife.bind(this);

        // Get instance of Singleton Network Helper
        networkHelper = NetworkHelper.getInstance();

        // Set up and initialize
        initialize();
    }

    private void initialize() {
        catalogElements = new ArrayList<>();
        catalogAdapter = new CatalogAdapter(this, catalogElements);
        listView.setAdapter(catalogAdapter);
        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                loadMoreResults();
                return true;
            }
        });
    }

    private void loadMoreResults() {
        networkHelper.loadMoreResults(new CustomCallback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, JSONObject jsonObject) throws JSONException {
                try {
                    if (jsonObject.length() > 0) {
                        catalogElements.addAll(CatalogElement.parseJsonResponse(jsonObject));
                        updateUI(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick(R.id.searchButton)
    public void searchCatalog(View view) {
        String searchText = searchEditText.getText().toString();
        if (searchText.length() > 0) {
            dismissKeyboard();
            getCatalog(searchText);
        }
    }

    // Enable Search Button only when search box has text
    @OnTextChanged(R.id.searchEditText)
    public void textChanged(CharSequence text) {
        if (text.length() > 0) {
            searchButton.setEnabled(true);
        } else {
            searchButton.setEnabled(false);
        }
    }

    private void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }

    private void getCatalog(String searchText) {
        final KProgressHUD hud = createProgressHUD();
        hud.show();
        networkHelper.getCatalog(searchText, new CustomCallback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, JSONObject jsonObject) throws JSONException {
                try {
                    if (jsonObject.length() > 0) {
                        catalogElements.clear();
                        catalogElements.addAll(CatalogElement.parseJsonResponse(jsonObject));
                        hud.dismiss();
                        updateUI(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateUI(final boolean scrollUp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                catalogAdapter.notifyDataSetChanged();
                if (scrollUp) {
                    listView.setSelectionAfterHeaderView();
                }
            }
        });
    }

    private KProgressHUD createProgressHUD() {
        return KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }
}
