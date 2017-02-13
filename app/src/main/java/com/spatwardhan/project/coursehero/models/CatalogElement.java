package com.spatwardhan.project.coursehero.models;

import com.spatwardhan.project.coursehero.helpers.ApiEndpointHelper;
import com.spatwardhan.project.coursehero.helpers.PartnersHelper;
import com.spatwardhan.project.coursehero.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CatalogElement implements Serializable {
    private String id;
    private String photoUrl;
    private String name;
    private String description;
    private ArrayList<Partner> partners;

    // Keys used for parsing response
    private static final String LINKED = "linked";
    private static final String ID = "id";
    private static final String ELEMENTS = "elements";
    private static final String ENTRIES = "entries";
    private static final String RESOURCE_NAME = "resourceName";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PARTNER_IDS = "partnerIds";

    /* Response is parsed in 4 steps:
     *   1) Parse Partners Response and build Partners map of <partnerID, Partner>
     *   2) Parse Courses response in linked and build Courses map of <courseID, Course>
     *   3) Parse onDemandSpecializations in response in linked and build Specialization map of <specializationID, Specialization>
     *   4) Parse entries array in elements response. For every entry, get either course or specialization from the map
     *      based on id and add it to results.
     */
    public static List<CatalogElement> parseJsonResponse(JSONObject responseObject) throws JSONException {
        JSONObject linkedObject = responseObject.getJSONObject(LINKED);

        parsePartnersResponse(linkedObject);
        Map<Integer, CatalogElement> coursesMap = parseCoursesResponse(linkedObject);
        Map<Integer, CatalogElement> specializationsMap = parseSpecializationsResponse(linkedObject);

        return parseEntriesResponse(responseObject, coursesMap, specializationsMap);
    }

    private static void parsePartnersResponse(JSONObject linkedObject) throws JSONException {
        JSONArray partnersArray = linkedObject.getJSONArray(ApiEndpointHelper.PARTNERS_VERSION);

        if (!Utils.isNullOrEmpty(partnersArray)) {
            Map<Integer, Partner> partnerMap = PartnersHelper.getPartnerMap();
            // Get length of Array instead of calling .length() many times
            // in for loop for better efficiency
            // Source : https://developer.android.com/training/articles/perf-tips.html#Loops
            int length = partnersArray.length();
            int id;
            for (int i = 0; i < length; i++) {
                id = partnersArray.getJSONObject(i).getInt(ID);
                if (!partnerMap.containsKey(id)) {
                    partnerMap.put(id, new Partner(partnersArray.getJSONObject(i)));
                }
            }
        }
    }

    private static Map<Integer, CatalogElement> parseCoursesResponse(JSONObject linkedObject) throws JSONException {
        JSONArray coursesArray = linkedObject.getJSONArray(ApiEndpointHelper.COURSES_VERSION);
        Map<Integer, CatalogElement> coursesMap = null;
        if (!Utils.isNullOrEmpty(coursesArray)) {
            // Using a hashmap for faster looks ups
            coursesMap = new HashMap<>();
            CatalogElement courseElement;
            int length = coursesArray.length();
            for (int i = 0; i < length; i++) {
                courseElement = new Course(coursesArray.getJSONObject(i));
                coursesMap.put(courseElement.hashCode(), courseElement);
            }
        }
        return coursesMap;
    }

    private static Map<Integer, CatalogElement> parseSpecializationsResponse(JSONObject linkedObject) throws JSONException {
        JSONArray specializationsArray = linkedObject.getJSONArray(ApiEndpointHelper.SPECIALIZATIONS_VERSION);
        Map<Integer, CatalogElement> specializationsMap = null;
        if (!Utils.isNullOrEmpty(specializationsArray)) {
            // Using a hashmap for faster looks ups
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
        JSONArray elementsArray = responseObject.getJSONArray(ELEMENTS);
        JSONObject first = elementsArray.getJSONObject(0);
        JSONArray entriesArray = first.getJSONArray(ENTRIES);

        Map<Integer, CatalogElement> sourceMap;
        if (!Utils.isNullOrEmpty(entriesArray)) {
            for (int i = 0; i < entriesArray.length(); i++) {
                JSONObject entryObject = entriesArray.getJSONObject(i);
                String resourceName = entryObject.getString(RESOURCE_NAME);
                sourceMap = (resourceName.equalsIgnoreCase(ApiEndpointHelper.COURSES_VERSION)) ? coursesMap : specializationsMap;

                Integer id = entryObject.getString(ID).hashCode();
                if (sourceMap != null && sourceMap.containsKey(id)) {
                    result.add(sourceMap.get(id));
                }
            }
        }
        return result;
    }

    // Method called by both subclasses which implements common functionality
    // of getting id, name, description and partners
    final void buildObject(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getString(ID);
        name = jsonObject.getString(NAME);
        description = jsonObject.getString(DESCRIPTION);
        partners = new ArrayList<>();

        Map<Integer, Partner> partnerMap = PartnersHelper.getPartnerMap();
        JSONArray partnersArray = jsonObject.getJSONArray(PARTNER_IDS);
        if (partnersArray != null && partnersArray.length() > 0) {
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

    public ArrayList<Partner> getPartners() {
        return partners;
    }
}
