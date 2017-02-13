package com.spatwardhan.project.coursehero.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Specialization extends CatalogElement {
    private static final String courses = " courses";

    public int getCourseNum() {
        return courseNum;
    }

    public String getCoursesString() {
        return courseNum + courses;
    }

    private int courseNum;

    public Specialization(JSONObject specializationObject) throws JSONException {
        setPhotoUrl(specializationObject.getString("logo"));
        courseNum = specializationObject.getJSONArray("courseIds").length();
        super.buildObject(specializationObject);
    }
}
