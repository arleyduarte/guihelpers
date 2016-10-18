/*******************************************************************************
 * Copyright (c) 2014. Zyght
 * All rights reserved.
 ******************************************************************************/

package com.amdp.android.guihelpers.apihandler;


import android.util.Log;

import com.amdp.android.entity.BasicEntity;
import com.amdp.android.entity.BasicEntityBLL;
import com.amdp.android.guihelpers.R;
import com.amdp.android.network.APIResourceHandler;
import com.amdp.android.network.APIResponse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by Arley Mauricio Duarte
 */
public class GetEntityAPIHandler extends APIResourceHandler {

    private String userId = "";
    private static final String TAG = GetEntityAPIHandler.class.getSimpleName();
    static String errorToShow = "";


    public GetEntityAPIHandler() {


    }



    @Override
    public void handlerAPIResponse(APIResponse apiResponse) {




        if (apiResponse.getStatus().isSuccess()) {
            BasicEntityBLL entityBLL =  BasicEntityBLL.getInstance();

            entityBLL.clear();

            try {

                JSONArray jsonArray = new JSONArray(apiResponse.getRawResponse());
                for (int i = 0; i < jsonArray.length(); i++) {
                    BasicEntity basicEntity = fromJson(jsonArray.getString(i));
                    if (basicEntity != null)
                        entityBLL.add(basicEntity);
                }

                if (entityBLL.getItems().size() > 0) {

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


    public static BasicEntity fromJson(String apiResponse) {
        BasicEntity item = null;
        if (!apiResponse.isEmpty()) {
            try {
                JSONObject result = new JSONObject(apiResponse);


                item = new BasicEntity();
                item.setEntityId(result.getString("id").trim());
                item.setName(result.getString("name").trim());


            } catch (JSONException e) {

                errorToShow = e.getMessage();

                Log.e(TAG, e.getMessage());
            } catch (NumberFormatException e) {
                errorToShow = e.getMessage();
                Log.e(TAG, e.getMessage());
            }
        }

        return item;
    }


    @Override
    public HashMap<String, String> getBodyParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        return params;
    }

    @Override
    public String getServiceURL() {
        return getContext().getResources().getString(R.string.BASE_SERVICE_URL) + "encuestas";
    }


}
