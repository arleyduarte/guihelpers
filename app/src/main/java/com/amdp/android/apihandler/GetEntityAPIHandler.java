/*******************************************************************************
 * Copyright (c) 2014. Zyght
 * All rights reserved.
 ******************************************************************************/

package com.amdp.android.apihandler;


import android.text.TextUtils;
import android.util.Log;

import com.amdp.android.entity.BasicEntity;
import com.amdp.android.entity.BasicEntityBLL;
import com.amdp.android.guihelpers.R;
import com.amdp.android.network.APIResourceHandler;
import com.amdp.android.network.APIResponse;
import com.amdp.android.services.PendingRequestManager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by Arley Mauricio Duarte
 */
public class GetEntityAPIHandler extends APIResourceHandler {

    private String userId = "";
    protected static  String TAG = GetEntityAPIHandler.class.getSimpleName();
    static String message = "";


    public GetEntityAPIHandler() {


    }



    @Override
    public void handlerAPIResponse(APIResponse apiResponse) {

        if(apiResponse.getStatus().isSuccess()) {
            PendingRequestManager.getInstance().saveSuccessfullRespone(getContext(), TAG, apiResponse);
        }
        else{

            apiResponse = PendingRequestManager.getInstance().getLastSuccessfullRespone(getContext(), TAG);
        }


        if (!TextUtils.isEmpty(apiResponse.getRawResponse())) {
            BasicEntityBLL entityBLL =  BasicEntityBLL.getInstance();

            entityBLL.clear();

            try {

                JSONArray jsonArray = new JSONArray(apiResponse.getRawResponse());
                for (int i = 0; i < jsonArray.length(); i++) {
                    BasicEntity basicEntity = fromJson(jsonArray.getString(i));
                    if (basicEntity != null)
                        entityBLL.add(basicEntity);
                }



            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                message = e.getMessage();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                message = e.getMessage();
            }

        }

        postProcess(message);


    }

    public void postProcess(String message){

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

                message = e.getMessage();

                Log.e(TAG, e.getMessage());
            } catch (NumberFormatException e) {
                message = e.getMessage();
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
