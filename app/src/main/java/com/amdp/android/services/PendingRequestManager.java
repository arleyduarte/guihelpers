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
    private   List<APIRequest> apiRequests;
    private int popIndex = 0;

    public static PendingRequestManager getInstance() {
        return ourInstance;
    }

    public void  push(APIRequest apiRequest){
        APIRequest ap = new APIRequest();
        ap.setFullURL(apiRequest.getFullURL());
        ap.setMethod(apiRequest.getMethod());
        ap.setParams(apiRequest.getParams());
        ap.setService(apiRequest.getService());
        ap.setEntityId(apiRequest.getEntityId());
        ap.serialized();

        Log.i(TAG, "ap:"+ap.getParams().toString());

    }


    public void  pop(){
        apiRequests = APIRequest.listAll(APIRequest.class);

        popNext();

    }

    private void popNext(){
        if(apiRequests.size() > popIndex){

            APIRequest request =  apiRequests.get(popIndex);

            Log.i(TAG, "pop :"+popIndex+apiRequests);
            request.restore();
            request.delete();
            currentRequest = request;
            sendRequest(currentRequest);

            popIndex++;
        }
    }

    private  synchronized  void sendRequest(APIRequest request){
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
        popNext();
    }

    @Override
    public void didNotSuccessfully(String s) {
        push(currentRequest);
        popNext();
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
