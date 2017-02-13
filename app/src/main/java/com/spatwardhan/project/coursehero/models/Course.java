package com.spatwardhan.project.coursehero.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Course extends CatalogElement {

    public Course(JSONObject courseObject) throws JSONException {
        setPhotoUrl(courseObject.getString("photoUrl"));
        super.buildObject(courseObject);
    }
}
