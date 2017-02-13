package com.spatwardhan.project.coursehero.models;

import org.json.JSONException;
import org.json.JSONObject;

class Course extends CatalogElement {
    // Key for parsing
    private static final String PHOTO_URL = "photoUrl";

    Course(JSONObject courseObject) throws JSONException {
        setPhotoUrl(courseObject.getString(PHOTO_URL));
        super.buildObject(courseObject);
    }
}
