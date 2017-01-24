/*******************************************************************************
 * Copyright (c) 2014. Zyght
 * All rights reserved.
 *
 ******************************************************************************/

package com.amdp.android.survey.entities;

import com.amdp.android.entity.APIEntity;
import com.orm.SugarRecord;



/**
 * Created by Arley Mauricio Duarte
 */
public class SurveyRegister implements APIEntity {



    private String entityId = "";
    private String survey = "";
    private String userId = "";
    private String parameters = "";

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }



    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }



    @Override
    public String getEntityId() {
        return entityId;
    }

    @Override
    public String getName() {
        return userId;
    }




}
