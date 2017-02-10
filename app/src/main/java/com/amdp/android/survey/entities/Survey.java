package com.amdp.android.survey.entities;

import com.amdp.android.entity.APIEntity;
import com.orm.SugarRecord;

import org.json.JSONArray;

/**
 * Created by arley on 9/24/16.
 */
public class Survey  implements APIEntity {
    private String entityId = "";
    private String survey = "";
    private String name = "";
    private String applyTo = "";

    public String getEntityId() {
        return entityId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }



    public void setName(String name) {
        this.name = name;
    }


    public String getApplyTo() {
        return applyTo;
    }

    public void setApplyTo(String applyTo) {
        this.applyTo = applyTo;
    }

    public boolean surveyIsApplicable(String entityId) {
        boolean beRelated = false;

        try {
            JSONArray jsonArray = new JSONArray(this.getApplyTo());
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getString(i).equals(entityId)) {
                    beRelated = true;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return beRelated;
    }

    public int compareTo(APIEntity another) {
        return getName().compareTo(another.getName());
    }
}
