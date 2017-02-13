package com.spatwardhan.project.coursehero.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Partner {
    // Keys for parsing
    private static final String ID = "id";
    private static final String NAME = "name";

    private int id;
    private String name;

    Partner(JSONObject partnersObject) throws JSONException {
        id = partnersObject.getInt(ID);
        name = partnersObject.getString(NAME);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        Partner objPartner = (Partner) obj;
        return id == objPartner.getId();
    }
}
