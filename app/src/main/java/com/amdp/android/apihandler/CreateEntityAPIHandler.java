package com.amdp.android.apihandler;

import com.amdp.android.models.APIRequest;
import com.amdp.android.network.APIResourceHandler;
import com.amdp.android.network.APIResponse;
import com.amdp.android.services.PendingRequestManager;


import java.util.HashMap;

/**
 * Created by arley on 9/23/16.
 */
public class CreateEntityAPIHandler extends APIResourceHandler {

    private APIRequest apiRequest;


    public CreateEntityAPIHandler(APIRequest apiRequest) {
        this.apiRequest = apiRequest;
    }




    @Override
    public String getServiceURL() {
        return getContext().getResources().getString(com.amdp.android.guihelpers.R.string.BASE_SERVICE_URL) + apiRequest.getService();
    }

    @Override
    public void handlerAPIResponse(APIResponse apiResponse) {
        if(apiResponse.getStatus().isSuccess())
        {
            getResponseActionDelegate().didSuccessfully(apiResponse.getRawResponse());
        }else {
            apiRequest.setFullURL(getServiceURL());
            PendingRequestManager.getInstance().push(apiRequest);
            getResponseActionDelegate().didNotSuccessfully(apiResponse.getRawResponse());
        }
    }



    public HashMap<String, String> getBodyParams() {

        return apiRequest.getParams();
    }

}
