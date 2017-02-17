package com.amdp.android.services;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.amdp.android.models.APIRequest;import com.amdp.android.network.APIResourceHandler;import com.amdp.android.network.APIResponse;import com.amdp.android.network.ResponseActionDelegate;

import java.util.HashMap;
import java.util.List;


/**
 * Created by arley on 12/13/16.
 */

public class PendingRequestManager extends APIResourceHandler implements ResponseActionDelegate {

    private static final String TAG = "PendingRequestManager";
    private APIRequest currentRequest;
    private static PendingRequestManager ourInstance = new PendingRequestManager();

    public static PendingRequestManager getInstance() {
        return ourInstance;
    }

    public void  push(APIRequest apiRequest){
        apiRequest.serialized();

    }


    public void  pop(){
        List<APIRequest> apiRequests = APIRequest.listAll(APIRequest.class);



        for(APIRequest request : apiRequests){
            Log.i(TAG, "pop:"+apiRequests);
            request.restore();
            request.delete();
            currentRequest = request;
            sendRequest(currentRequest);
        }
    }

    private void sendRequest(APIRequest request){
        setShowWaitingDialog(false);
        setRequestHandle(this, null);

    }

    @Override
    public void handlerAPIResponse(APIResponse apiResponse) {
        if(apiResponse.getStatus().isSuccess())
        {
            getResponseActionDelegate().didSuccessfully(apiResponse.getRawResponse());
        }else {
            getResponseActionDelegate().didNotSuccessfully(apiResponse.getRawResponse());
        }
    }


    @Override
    public String getServiceURL() {
        return currentRequest.getFullURL();
    }

    @Override
    public void didSuccessfully(String s) {
        Log.i(TAG, "didSuccessfully:"+s);
    }

    @Override
    public void didNotSuccessfully(String s) {
        push(currentRequest);
    }


    @Override
    public HashMap<String, String> getBodyParams() {

        return currentRequest.getParams();
    }

    public APIResponse getLastSuccessfullRespone(Context context, String apiHandlerName){
        APIResponse response = new APIResponse();

        if(context != null){
            SharedPreferences settings = context.getSharedPreferences(TAG, 0);
            response.setRawResponse(settings.getString(apiHandlerName, ""));
        }


        return  response;
    }

    public void saveSuccessfullRespone(Context context, String apiHandlerName, APIResponse response) {

        if(context != null){
            SharedPreferences settings = context.getSharedPreferences(TAG, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(apiHandlerName,response.getRawResponse());
            editor.commit();
        }

    }
}
