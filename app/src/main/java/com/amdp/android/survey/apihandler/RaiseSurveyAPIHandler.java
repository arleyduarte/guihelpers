/*******************************************************************************
 * Copyright (c) 2014. Zyght
 * All rights reserved. 
 *
 ******************************************************************************/

package com.amdp.android.survey.apihandler;


import com.amdp.android.guihelpers.R;
import com.amdp.android.guihelpers.utils.GlobalVariables;
import com.amdp.android.network.APIResourceHandler;
import com.amdp.android.network.APIResponse;
import com.amdp.android.survey.entities.SurveyRegister;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Arley Mauricio Duarte
 */
public class RaiseSurveyAPIHandler extends APIResourceHandler {

    private SurveyRegister surveyRegister;


    public RaiseSurveyAPIHandler(SurveyRegister surveyRegister) {
        this.surveyRegister = surveyRegister;

    }


    @Override
    public void handlerAPIResponse(APIResponse apiResponse) {


        if (apiResponse.getStatus().isSuccess()) {

            if (surveyRegister.getEntityId() != null && surveyRegister.getEntityId().length() > 0) {
                getResponseActionDelegate().didSuccessfully(surveyRegister.getEntityId());
            } else {
                getResponseActionDelegate().didSuccessfully(apiResponse.getRawResponse());
            }


        } else {

            surveyRegister.setEntityId(String.valueOf(System.currentTimeMillis()));

            //TODO: Service
            //PendingRegistersStack.getStack().push(surveyRegister);
            getResponseActionDelegate().didNotSuccessfully("La encuesta fue guardada y se enviara cuando exista conexión a red");

        }


    }



    @Override
    public HashMap<String, String> getBodyParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", surveyRegister.getUserId());
        params.put("survey", surveyRegister.getSurvey());
        params.put("name", surveyRegister.getParameters());


        HashMap<String, String> additional =  GlobalVariables.getInstance().getVariables(GlobalVariables.SURVEY_MODULE);

        if(!additional.isEmpty()){
            params.putAll(additional);
        }

        return params;
    }

    @Override
    public String getServiceURL() {
        return getContext().getResources().getString(R.string.BASE_SERVICE_URL)+ "/survey";
    }



}
