package com.spatwardhan.project.coursehero.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zky447 on 2/11/17.
 */

public abstract class CatalogElement {
    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getUniversity() {
        return university;
    }

    void setUniversity(String university) {
        this.university = university;
    }

    private String id;
    private String photoUrl;
    private String name;
    private String description;
    private String university;

    public static List<CatalogElement> parseJsonResponse(JSONObject responseObject) throws JSONException {
        List<CatalogElement> result = new ArrayList<>();
        JSONObject linkedObject = responseObject.getJSONObject("linked");
        JSONArray coursesArray = linkedObject.getJSONArray("courses.v1");

        Set<CatalogElement> coursesSet;
        if (coursesArray != null && coursesArray.length() > 0) {
            coursesSet = new HashSet<>();

            // Get length of Array instead of calling .length() many times
            // in for loop for better efficiency
            // Source : https://developer.android.com/training/articles/perf-tips.html#Loops
            int length = coursesArray.length();
            for (int i = 0; i < length; i++) {
                coursesSet.add(new Course(coursesArray.getJSONObject(i)));
            }
        }

        JSONArray specializationsArray = linkedObject.getJSONArray("onDemandSpecializations.v1");
        Set<CatalogElement> specializationsSet;

        if(specializationsArray != null && specializationsArray.length() > 0){

        }

        return result;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        CatalogElement element = (CatalogElement) obj;
        return id.equals(element.getId());
    }
}
