package com.amdp.android.survey.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amdp.android.guihelpers.R;
import com.amdp.android.guihelpers.formgenerator.FormActivity;
import com.amdp.android.guihelpers.photo.IPhotoResultDelegate;
import com.amdp.android.guihelpers.photo.PhotoManager;
import com.amdp.android.network.ResponseActionDelegate;
import com.amdp.android.survey.apihandler.RaiseSurveyAPIHandler;
import com.amdp.android.survey.entities.Survey;
import com.amdp.android.survey.entities.SurveyBLL;
import com.amdp.android.survey.entities.SurveyRegister;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


public class QuestionsByOneForm extends FormActivity implements ResponseActionDelegate, IPhotoResultDelegate {

    private Survey currentSurvey;
    private ArrayList<String> splitQuestion = new ArrayList<String>();
    private int questionIndex = 0;
    private JSONArray jsonResponses = new JSONArray();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentSurvey = SurveyBLL.getInstance().getSelectedSurvey();

        String su = currentSurvey.getSurvey();



        try {
            String name;
            JSONObject schema  = new JSONObject( su );
            JSONArray names = schema.names();
            JSONObject property;

            JSONArray ja = new JSONArray();

            for( int i= 0; i < names.length(); i++ ) {
                name = names.getString(i);

                if (name.equals(SCHEMA_KEY_META)) continue;

                property = schema.getJSONObject(name);
                ja.put(property);


                String s = surveyJsonAdapter(name, property.toString());
                splitQuestion.add(s);

            }

            } catch (JSONException e) {
            e.printStackTrace();
        }



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(!splitQuestion.isEmpty()){
            String question = splitQuestion.get(0);
            setupQuestion(question);
        }

    }

    private String surveyJsonAdapter(String name, String jsonQuestion){
        String jsonSurvey  = "{\""+name+"\":"+jsonQuestion+"}";
        return  jsonSurvey;
    }


    private void setupQuestion(String question){
        generateForm(question);
        Button myButton = new Button(this);
        myButton.setText("Siguiente");
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showNextQuestion();
            }
        });

        myButton.setBackgroundColor(getResources().getColor(R.color.corporate_color));
        myButton.setTextColor(Color.WHITE);
        _layout.addView(myButton);
    }

    private void showNextQuestion(){
        jsonResponses.put(save());

        questionIndex++;
        if(splitQuestion.size() > questionIndex){
            String question = splitQuestion.get(questionIndex);
            setupQuestion(question);
        }

        else{
            sendSurvey();
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendSurvey(){

        //JSONObject data = save();
        SurveyRegister sr  =new SurveyRegister();
        sr.setSurvey(jsonResponses.toString());

        SharedPreferences settings = getSharedPreferences(getBaseContext().getResources().getString(R.string.PREFS_NAME), 0);
        String userId = settings.getString("userId", "");
        sr.setUserId(userId);
        sr.setParameters(currentSurvey.getName());

        RaiseSurveyAPIHandler rh = new RaiseSurveyAPIHandler(sr);
        rh.setRequestHandle(this, this);

    }

    @Override
    public void didSuccessfully(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        PhotoManager.getInstance().displaySelectImage(this, this);
    }

    @Override
    public void didNotSuccessfully(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        PhotoManager.getInstance().displaySelectImage(this, this);
    }

    @Override
    public void takePhoto() {
        finish();
    }

    @Override
    public void takeImageFromSD() {
        finish();
    }
}
