package com.spatwardhan.project.coursehero.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zky447 on 2/11/17.
 */

public class Partner {
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private int id;
    private String name;

    Partner(JSONObject partnersObject) throws JSONException {
        id = partnersObject.getInt("id");
        name = partnersObject.getString("name");
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
