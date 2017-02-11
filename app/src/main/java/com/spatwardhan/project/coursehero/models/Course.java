package com.spatwardhan.project.coursehero.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zky447 on 2/11/17.
 */

public class Course extends CatalogElement {

    public Course(JSONObject courseObject) throws JSONException {
        setPhotoUrl(courseObject.getString("photoUrl"));
        super.buildObject(courseObject);
    }
}
