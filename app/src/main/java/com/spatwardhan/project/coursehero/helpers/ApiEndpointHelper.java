package com.spatwardhan.project.coursehero.helpers;

import com.spatwardhan.project.coursehero.BuildConfig;

public class ApiEndpointHelper {
    // Dot and comma used for string construction
    private static final String DOT = ".";
    private static final String COMMA = ",";

    // Intermediate  strings which are highly configurable
    // Especially for changing Version numbers for courses, onDemandSpecializations and partners
    private static final String VERSION_1 = "v1";
    private static final String VERSION_2 = "v2";
    private static final String CATALOG_RESULTS = "catalogResults";
    private static final String CATALOG_RESULTS_VERSION = CATALOG_RESULTS + DOT + VERSION_2;
    private static final String COURSES = "courses";
    private static final String COURSE_ID = "courseId";
    private static final String COURSES_PARAMS = "(name,photoUrl,partnerIds,description)";
    private static final String SPECIALIZATIONS = "onDemandSpecializations";
    private static final String SPECIALIZATION_ID = "onDemandSpecializationId";
    private static final String SPECIALIZATIONS_PARAMS = "(name,logo,courseIds,partnerIds)";
    private static final String PARTNERS = "partners";
    private static final String PARTNERS_PARAMS = "(name)";
    private static final String INCLUDES_PARAMS = "(partnerIds)";

    // API Endpoints
    static final String apiEndpoint = String.format("%s/", BuildConfig.API_URL);
    static final String API_GET_CATALOG_RESULTS = CATALOG_RESULTS_VERSION;

    // These strings are keys used in parsing of JSON response by CatalogElement
    public static final String COURSES_VERSION = COURSES + DOT + VERSION_1;
    public static final String PARTNERS_VERSION = PARTNERS + DOT + VERSION_1;
    public static final String SPECIALIZATIONS_VERSION = SPECIALIZATIONS + DOT + VERSION_1;

    // Construct fields and includes part of API query
    // Advantage of this approach is that parameters and versions can be easily changed
    static final String API_FIELDS = COURSE_ID + COMMA + SPECIALIZATION_ID + COMMA
            + COURSES_VERSION + COURSES_PARAMS + COMMA
            + SPECIALIZATIONS_VERSION + SPECIALIZATIONS_PARAMS + COMMA
            + PARTNERS_VERSION + PARTNERS_PARAMS;

    static final String API_INCLUDES = COURSE_ID + COMMA + SPECIALIZATION_ID + COMMA + COURSES_VERSION + INCLUDES_PARAMS;

}
