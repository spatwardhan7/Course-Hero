package com.spatwardhan.project.coursehero.models;

import com.spatwardhan.project.coursehero.helpers.PartnersHelper;
import com.spatwardhan.project.coursehero.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ArrayList<Partner> partners;

    private static void parsePartnersResponse(JSONObject linkedObject) throws JSONException {
        JSONArray partnersArray = linkedObject.getJSONArray("partners.v1");

        if (!Utils.isNullOrEmpty(partnersArray)) {
            Map<Integer, Partner> partnerMap = PartnersHelper.getPartnerMap();
            int length = partnersArray.length();
            int id;
            for (int i = 0; i < length; i++) {
                id = partnersArray.getJSONObject(i).getInt("id");
                if (!partnerMap.containsKey(id)) {
                    partnerMap.put(id, new Partner(partnersArray.getJSONObject(i)));
                }
            }
        }
    }

    private static Map<Integer, CatalogElement> parseCoursesResponse(JSONObject linkedObject) throws JSONException {
        JSONArray coursesArray = linkedObject.getJSONArray("courses.v1");
        Map<Integer, CatalogElement> coursesMap = null;
        if (!Utils.isNullOrEmpty(coursesArray)) {
            coursesMap = new HashMap<>();
            CatalogElement courseElement;
            // Get length of Array instead of calling .length() many times
            // in for loop for better efficiency
            // Source : https://developer.android.com/training/articles/perf-tips.html#Loops
            int length = coursesArray.length();
            for (int i = 0; i < length; i++) {
                courseElement = new Course(coursesArray.getJSONObject(i));
                coursesMap.put(courseElement.hashCode(), courseElement);
            }
        }
        return coursesMap;
    }

    private static Map<Integer, CatalogElement> parseSpecializationsResponse(JSONObject linkedObject) throws JSONException {
        JSONArray specializationsArray = linkedObject.getJSONArray("onDemandSpecializations.v1");
        Map<Integer, CatalogElement> specializationsMap = null;
        if (!Utils.isNullOrEmpty(specializationsArray)) {
            specializationsMap = new HashMap<>();
            CatalogElement specializationElement;
            int length = specializationsArray.length();
            for (int i = 0; i < length; i++) {
                specializationElement = new Specialization(specializationsArray.getJSONObject(i));
                specializationsMap.put(specializationElement.hashCode(), specializationElement);
            }
        }
        return specializationsMap;
    }

    private static List<CatalogElement> parseEntriesResponse(JSONObject responseObject, Map<Integer, CatalogElement> coursesMap, Map<Integer, CatalogElement> specializationsMap) throws JSONException {
        List<CatalogElement> result = new ArrayList<>();
        JSONArray elementsArray = responseObject.getJSONArray("elements");
        JSONObject first = elementsArray.getJSONObject(0);
        JSONArray entriesArray = first.getJSONArray("entries");

        Map<Integer, CatalogElement> sourceMap;
        if (!Utils.isNullOrEmpty(entriesArray)) {
            for (int i = 0; i < entriesArray.length(); i++) {
                JSONObject entryObject = entriesArray.getJSONObject(i);
                String resourceName = entryObject.getString("resourceName");
                Integer id = entryObject.getString("id").hashCode();

                sourceMap = (resourceName.equals("courses.v1")) ? coursesMap : specializationsMap;

                if (sourceMap != null && sourceMap.containsKey(id)) {
                    result.add(sourceMap.get(id));
                }
            }
        }
        return result;
    }

    public static List<CatalogElement> parseJsonResponse(JSONObject responseObject) throws JSONException {
        JSONObject linkedObject = responseObject.getJSONObject("linked");

        parsePartnersResponse(linkedObject);
        Map<Integer, CatalogElement> coursesMap = parseCoursesResponse(linkedObject);
        Map<Integer, CatalogElement> specializationsMap = parseSpecializationsResponse(linkedObject);

        return parseEntriesResponse(responseObject, coursesMap, specializationsMap);
    }

    void buildObject(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getString("id");
        name = jsonObject.getString("name");
        description = jsonObject.getString("description");

        Map<Integer, Partner> partnerMap = PartnersHelper.getPartnerMap();
        JSONArray partnersArray = jsonObject.getJSONArray("partnerIds");
        if (partnersArray != null && partnersArray.length() > 0) {
            partners = new ArrayList<>();
            int length = partnersArray.length();
            int id;
            for (int i = 0; i < length; i++) {
                id = Integer.valueOf(partnersArray.getString(i));
                if (partnerMap.containsKey(id)) {
                    partners.add(partnerMap.get(id));
                }
            }
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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
