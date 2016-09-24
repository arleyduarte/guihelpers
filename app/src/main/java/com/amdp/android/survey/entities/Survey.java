package com.amdp.android.survey.entities;

import com.amdp.android.entity.APIEntity;
import com.orm.SugarRecord;

/**
 * Created by arley on 9/24/16.
 */
public class Survey extends SugarRecord implements APIEntity {
    private String entityId = "";
    private String survey = "";
    private String name = "";

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



    public int compareTo(APIEntity another) {
        return getName().compareTo(another.getName());
    }
}
