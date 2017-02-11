package com.spatwardhan.project.coursehero.helpers;

import com.spatwardhan.project.coursehero.BuildConfig;

/**
 * Created by zky447 on 2/11/17.
 */

public class ApiEndpointHelper {
    static final String apiEndpoint = String.format("%s/", BuildConfig.API_URL);
    static final String API_GET_CATALOG_RESULTS  = "catalogResults.v2";
}
