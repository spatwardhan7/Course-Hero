package com.spatwardhan.project.coursehero.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zky447 on 2/11/17.
 */

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
