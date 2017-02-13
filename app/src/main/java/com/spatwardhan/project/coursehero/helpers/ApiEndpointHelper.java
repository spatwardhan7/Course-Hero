package com.spatwardhan.project.coursehero.helpers;

import com.spatwardhan.project.coursehero.BuildConfig;

public class ApiEndpointHelper {
    static final String apiEndpoint = String.format("%s/", BuildConfig.API_URL);
    static final String API_GET_CATALOG_RESULTS = "catalogResults.v2";

    static final String VERSION = "v1";
    static final String DOT = ".";
    static final String COMMA = ",";
    static final String COURSES = "courses";
    static final String COURSE_ID = "courseId";
    static final String COURSES_VERSION = COURSES + DOT + VERSION;
    static final String COURSES_PARAMS = "(name,photoUrl,partnerIds,description)";
    static final String SPECIALIZATIONS = "onDemandSpecializations";
    static final String SPECIALIZATION_ID = "onDemandSpecializationId";
    static final String SPECIALIZATIONS_VERSION = SPECIALIZATIONS + DOT + VERSION;
    static final String SPECIALIZATIONS_PARAMS = "(name,logo,courseIds,partnerIds)";
    static final String PARTNERS = "partners";
    static final String PARTNERS_VERSION = PARTNERS + DOT + VERSION;
    static final String PARTNERS_PARAMS = "(name)";
    static final String INCLUDES_PARAMS = "(partnerIds)";

    static final String API_FIELDS = COURSE_ID + COMMA + SPECIALIZATION_ID + COMMA
            + COURSES_VERSION + COURSES_PARAMS + COMMA
            + SPECIALIZATIONS_VERSION + SPECIALIZATIONS_PARAMS + COMMA
            + PARTNERS_VERSION + PARTNERS_PARAMS;
    //private static final String API_FIELDS_VAL = "courseId,onDemandSpecializationId,courses.v1(name,photoUrl,partnerIds,description),onDemandSpecializations.v1(name,logo,courseIds,partnerIds),partners.v1(name)";

    //private static final String API_INCLUDES_VAL = "courseId,onDemandSpecializationId,courses.v1(partnerIds)";

    static final String API_INCLUDES = COURSE_ID + COMMA + SPECIALIZATION_ID + COMMA + COURSES_VERSION + INCLUDES_PARAMS;

}
