package com.amdp.android.guihelpers.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by arley on 1/19/17.
 */
public class GlobalVariables {

    public static final String SURVEY_MODULE = "SURVEY_MODULE";

    private static GlobalVariables ourInstance = new GlobalVariables();

    public static GlobalVariables getInstance() {
        return ourInstance;
    }

    private GlobalVariables() {
    }

    private HashMap<String, String> variables = new HashMap<>();




    public HashMap<String, String> getVariables(String module) {
        HashMap<String, String> auxVariables = new HashMap<>();


        for (Map.Entry<String, String> entry : variables.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(key.startsWith(module)){
                auxVariables.put(key, value);
            }
        }




        return auxVariables;
    }



    public void put(String module, String key, String value){
        variables.put(module.concat(key), value);
    }



    public String getValue(String key){
        String value = "";

        if(variables.containsKey(key)){
            value = variables.get(key);
        }

        return  value;
    }


    public String getValue(String module, String key){
        String value = "";

        if(variables.containsKey(module.concat(key))){
            value = variables.get(module.concat(key));
        }

        return  value;
    }
}
