package com.spatwardhan.project.coursehero.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Specialization extends CatalogElement {
    // Keys for parsing
    private static final String LOGO = "logo";
    private static final String COURSE_IDS = "courseIds";

    private int courseNum;

    Specialization(JSONObject specializationObject) throws JSONException {
        setPhotoUrl(specializationObject.getString(LOGO));
        courseNum = specializationObject.getJSONArray(COURSE_IDS).length();
        super.buildObject(specializationObject);
    }

    private static final String courses = " courses";

    public int getCourseNum() {
        return courseNum;
    }

    public String getCoursesString() {
        return courseNum + courses;
    }
}
