package com.amdp.android.guihelpers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amdp.android.guihelpers.utils.FileUtils;
import com.amdp.android.survey.activities.SurveysListActivity;
import com.amdp.android.models.Survey;
import com.amdp.android.survey.entities.SurveyBLL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EmptyActivity extends AppCompatActivity {


    static String TAG = "";
    String errorToShow = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        handlerAPIResponse("");

        Intent intent = new Intent(this, SurveysListActivity.class);
        startActivity(intent);
    }


    public void handlerAPIResponse(String apiResponse) {



        String jsonLocation = FileUtils.loadJSONFromAsset(getApplicationContext(), "sv.json");

        //TODO
        String rawResponse = jsonLocation;//apiResponse.getRawResponse();

        SurveyBLL surveyBLL = SurveyBLL.getInstance();

        if (true) {
//            surveyBLL.clear();

            try {

                JSONArray jsonArray = new JSONArray(rawResponse);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Survey s = fromJson(jsonArray.getString(i));
                    if (s != null)
                        surveyBLL.addSurvey(s);
                }

                if (surveyBLL.getSurveys().size() > 0) {

                    surveyBLL.save();


                } else {

                }


            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                errorToShow = e.getMessage();
            } finally {


            }

        } else {

            surveyBLL.restore();



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

//                errorToShow = e.getMessage();

                Log.e(TAG, e.getMessage());
            } catch (NumberFormatException e) {
                //errorToShow = e.getMessage();
                Log.e(TAG, e.getMessage());
            }
        }

        return sv;
    }

}
