package com.amdp.android.models;


import com.amdp.android.entity.APIEntity;
import com.amdp.android.network.HttpMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orm.SugarRecord;

import java.lang.reflect.Type;
import java.util.HashMap;


/**
 * Created by arley on 12/15/16.
 */

public class APIRequest extends SugarRecord implements APIEntity {

    private String entityId;
    private HashMap params;
    private HttpMethod method;
    private String jsonParams = "";

    private String service = "";
    private String fullURL = "";

    public APIRequest(){
        setMethod(HttpMethod.POST);
    }

    public HashMap getParams() {
        return params;
    }

    public void setParams(HashMap params) {
        this.params = params;
    }



    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public APIRequest(HashMap params, String service){
        this.params = params;
        this.service = service;
        setMethod(HttpMethod.POST);
    }

    @Override
    public String getEntityId() {
        return entityId;
    }

    @Override
    public String getName() {
        return service;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getFullURL() {
        return fullURL;
    }

    public void setFullURL(String fullURL) {
        this.fullURL = fullURL;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void serialized(){
        Gson gson = new Gson();
        this.jsonParams = gson.toJson(params);
        this.save();
    }

    public void restore(){
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<HashMap<String, String>>(){}.getType();
        params = gson.fromJson(jsonParams, stringStringMap);
    }

    public String toString(){
        return  "fullURL"+fullURL+" Params:"+jsonParams;
    }
}
