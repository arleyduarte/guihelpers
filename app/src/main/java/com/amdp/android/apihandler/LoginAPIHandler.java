package com.amdp.android.apihandler;

import android.util.Log;

import com.amdp.android.network.APIResourceHandler;
import com.amdp.android.network.APIResponse;
import com.amdp.android.network.Session;
import com.amdp.android.services.PendingRequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by arley on 9/23/16.
 */
public class LoginAPIHandler extends APIResourceHandler {

    private static final String TAG = "ByMediaLoginAPIHandler";
    protected String username;
    protected String password;
    protected boolean supportOffline = true;

    public LoginAPIHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void handlerAPIResponse(APIResponse apiResponse) {
        if (apiResponse.getStatus().isSuccess()) {

            if (supportOffline) {
                PendingRequestManager.getInstance().saveSuccessfullRespone(getContext(), TAG, apiResponse);

            }
        } else {

            if (supportOffline) {
                apiResponse = PendingRequestManager.getInstance().getLastSuccessfullRespone(getContext(), TAG);
            }

        }

        extractToken(apiResponse.getRawResponse());

        onPostExecute(apiResponse);
    }

    @Override
    public HashMap<String, String> getBodyParams() {

        HashMap<String, String> params = new HashMap<>();

        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);

        return params;
    }

    public boolean isNeedAuthToken() {
        return false;
    }

    public void extractToken(String apiResponse) {

        String token = "";

        try {
            JSONObject object = new JSONObject(apiResponse);
            token = object.getString("access_token");


        } catch (JSONException e) {

        } finally {
            Session.getInstance().setAccessToken(token, getContext());
        }


    }

    public void onPostExecute(APIResponse apiResponse){
        Log.i(TAG, apiResponse.getRawResponse());
    }

}
