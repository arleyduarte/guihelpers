package com.amdp.android.survey.activities;

import android.app.AlertDialog;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amdp.android.guihelpers.R;
import com.amdp.android.guihelpers.formgenerator.FormActivity;
import com.amdp.android.guihelpers.photo.ContextPicker;

import com.amdp.android.guihelpers.utils.FileUtils;
import com.amdp.android.guihelpers.utils.GlobalVariables;
import com.amdp.android.network.MultipartLargeUtility;
import com.amdp.android.network.ResponseActionDelegate;
import com.amdp.android.survey.apihandler.RaiseSurveyAPIHandler;
import com.amdp.android.survey.entities.Survey;
import com.amdp.android.survey.entities.SurveyBLL;
import com.amdp.android.survey.entities.SurveyRegister;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class QuestionsByOneForm extends FormActivity implements ResponseActionDelegate {

    private static final String TAG = "QuestionsByOneForm";
    private Survey currentSurvey;
    private ArrayList<Question> splitQuestion = new ArrayList<Question>();
    private int questionIndex = 0;
    private JSONArray jsonResponses = new JSONArray();
    protected ActionBar actionBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContextPicker.getInstance().setPickFileResultDelegate(this);


        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        currentSurvey = SurveyBLL.getInstance().getSelectedSurvey();

        String su = currentSurvey.getSurvey();
        actionBar.setTitle(currentSurvey.getName());


        ArrayList<Question> auxQA = new ArrayList<Question>();
        try {
            String name;
            JSONObject schema = new JSONObject(su);
            JSONArray names = schema.names();
            JSONObject property;

            JSONArray ja = new JSONArray();

            for (int i = 0; i < names.length(); i++) {
                name = names.getString(i);

                if (name.equals(SCHEMA_KEY_META)) continue;

                property = schema.getJSONObject(name);
                int priority = property.getInt("priority");
                ja.put(property);


                String s = surveyJsonAdapter(name, property.toString());
                splitQuestion.add(new Question(s, priority));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        /*
        Question[] questions = new Question[splitQuestion.size()];

        int index = 0;
        for(Question q1 : auxQA){


            questions[index]= q1;
            index++;
        }*/

        Collections.sort(splitQuestion, new PriorityQuestionComparator());


        if (!splitQuestion.isEmpty()) {
            String question = ((Question) splitQuestion.get(0)).getQuestion();
            setupQuestion(question);
        }


       
    }

    private String surveyJsonAdapter(String name, String jsonQuestion) {
        String jsonSurvey = "{\"" + name + "\":" + jsonQuestion + "}";
        return jsonSurvey;
    }


    private void setupQuestion(String question) {
        generateForm(question);
        Button myButton = new Button(this);
        myButton.setText(getResources().getString(R.string.next_question));

        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showNextQuestion();
            }
        });

        // myButton.setBackgroundColor(getResources().getColor(R.color.corporate_color));
        myButton.setBackgroundColor(myButton.getContext().getResources().getColor(R.color.corporate_color));
        myButton.setTextColor(myButton.getContext().getResources().getColor(R.color.corporate_text_color));
        //myButton.setTextColor(Color.WHITE);
        _layout.addView(myButton);
    }

    private void showNextQuestion() {
        jsonResponses.put(save());

        questionIndex++;
        if (splitQuestion.size() > questionIndex) {

            String question = ((Question) splitQuestion.get(questionIndex)).getQuestion();
            setupQuestion(question);
        } else {
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

    private void sendSurvey() {

        //JSONObject data = save();
        SurveyRegister sr = new SurveyRegister();
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

        openDialog();
    }

    @Override
    public void didNotSuccessfully(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        openDialog();
    }





    public void openDialog() {
        final Dialog dialog = new Dialog(QuestionsByOneForm.this); // Context, this, etc.
        dialog.setContentView(R.layout.close_survey_origin);
        dialog.setTitle(R.string.finalize);

        Button buttonCamera = (Button) dialog.findViewById(R.id.buttonOk);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                                closeActivity();
                                            }
                                        }
        );

        dialog.show();
    }

    private void closeActivity(){
        this.finish();
    }


    @Override
    public void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private static final int FILE_SELECT_CODE = 0;

    private String pickedFileUUID = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();

                    Log.d(TAG, "File Uri: " + uri.toString());
                    // Get the path
                    String path = FileUtils.getPath(this, uri);
                    Log.d(TAG, "File Path: " + path);
                    pickedFileUUID = UUID.randomUUID().toString();
                    Send(path);


                    Toast.makeText(getApplicationContext(), "Su archivo se esta subiendo: " + path, Toast.LENGTH_LONG).show();

                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void Send(final String path) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    boolean useCSRF = true;
                    try {

                        MultipartLargeUtility multipart = new MultipartLargeUtility(getApplication().getResources().getString(R.string.UPLOAD_FILE_URL), "UTF-8", useCSRF);

                        multipart.addFormField("fileUUID", pickedFileUUID);

                        multipart.addFilePart("file", new File(path));
                        List<String> response = multipart.finish();
                        Log.w(TAG, "SERVER REPLIED:");
                        for (String line : response) {
                            Log.w(TAG, "Upload Files Response:::" + line);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public String getPickedFileUUID() {
        return pickedFileUUID;
    }
}
