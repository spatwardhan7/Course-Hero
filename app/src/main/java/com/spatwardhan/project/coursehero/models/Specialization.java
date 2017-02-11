package com.spatwardhan.project.coursehero.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zky447 on 2/11/17.
 */

public class Specialization extends CatalogElement {
    public int getCourseNum() {
        return courseNum;
    }

    private int courseNum;

    public Specialization(JSONObject specializationObject) throws JSONException {
        setPhotoUrl(specializationObject.getString("logo"));
        courseNum = specializationObject.getJSONArray("courseIds").length();
        super.buildObject(specializationObject);
    }
}
