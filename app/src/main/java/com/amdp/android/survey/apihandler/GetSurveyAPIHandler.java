/*******************************************************************************
 * Copyright (c) 2014. Zyght
 * All rights reserved.
 ******************************************************************************/

package com.amdp.android.survey.apihandler;


import android.text.TextUtils;
import android.util.Log;


import com.amdp.android.guihelpers.R;
import com.amdp.android.network.APIResourceHandler;
import com.amdp.android.network.APIResponse;
import com.amdp.android.network.PendingRequestManager;
import com.amdp.android.survey.entities.Survey;
import com.amdp.android.survey.entities.SurveyBLL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;


/**
 * Created by Arley Mauricio Duarte
 */
public class GetSurveyAPIHandler extends APIResourceHandler {

    private String userId = "";
    private static final String TAG = GetSurveyAPIHandler.class.getSimpleName();
    static String errorToShow = "";


    public GetSurveyAPIHandler(String userId) {
        this.userId = userId;

    }


    @Override
    public void handlerAPIResponse(APIResponse apiResponse) {


        if(apiResponse.getStatus().isSuccess()) {
            PendingRequestManager.getInstance().saveSuccessfullRespone(getContext(), TAG, apiResponse);
        }
        else{

            apiResponse = PendingRequestManager.getInstance().getLastSuccessfullRespone(getContext(), TAG);
        }


        SurveyBLL surveyBLL = SurveyBLL.getInstance();

        if (!TextUtils.isEmpty(apiResponse.getRawResponse())) {
            surveyBLL.clear();

            try {

                JSONArray jsonArray = new JSONArray(apiResponse.getRawResponse());
                for (int i = 0; i < jsonArray.length(); i++) {
                    Survey s = fromJson(jsonArray.getString(i));
                    if (s != null)
                        surveyBLL.addSurvey(s);
                }

                if (surveyBLL.getSurveys().size() > 0) {

                    surveyBLL.save();

                    getResponseActionDelegate().didSuccessfully("");
                } else {
                    getResponseActionDelegate().didNotSuccessfully(errorToShow);
                }


            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                errorToShow = e.getMessage();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                errorToShow = e.getMessage();
            } finally {
                if (errorToShow.length() > 0)
                    getResponseActionDelegate().didNotSuccessfully(errorToShow);
            }

        } else {


            getResponseActionDelegate().didSuccessfully("");


        }


    }


    public static Survey fromJson(String apiResponse) {
        Survey sv = null;
        if (!apiResponse.isEmpty()) {
            try {
                JSONObject result = new JSONObject(apiResponse);

                JSONObject meta = new JSONObject(result.getString("meta"));

                String name = meta.getString("Name").trim();
                sv = new Survey();
                sv.setName(name);
                sv.setSurvey(apiResponse);
                sv.setEntityId(name);

            } catch (JSONException e) {

                errorToShow = e.getMessage();

                Log.e(TAG, e.getMessage());
            } catch (NumberFormatException e) {
                errorToShow = e.getMessage();
                Log.e(TAG, e.getMessage());
            }
        }

        return sv;
    }


    @Override
    public HashMap<String, String> getBodyParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        return params;
    }

    @Override
    public String getServiceURL() {
        return getContext().getResources().getString(R.string.BASE_SERVICE_URL) + "loginmercy/encuestas";
    }


}
