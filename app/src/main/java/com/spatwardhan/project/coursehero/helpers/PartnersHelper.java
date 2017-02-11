package com.spatwardhan.project.coursehero.helpers;

import com.spatwardhan.project.coursehero.models.Partner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zky447 on 2/11/17.
 */

public class PartnersHelper {
    public static Map<Integer, Partner> getPartnerMap() {
        return partnerMap;
    }

    static Map<Integer, Partner> partnerMap = new HashMap<>();
}
