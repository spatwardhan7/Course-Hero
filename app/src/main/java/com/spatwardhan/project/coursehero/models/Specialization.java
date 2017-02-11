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
        setId(specializationObject.getString("id"));
        setPhotoUrl(specializationObject.getString("logo"));
        setName(specializationObject.getString("name"));
        setDescription(specializationObject.getString("description"));
        courseNum = specializationObject.getJSONArray("courseIds").length();
    }
}
