package com.amdp.android.survey.activities;

import android.content.Intent;
import android.text.TextUtils;

import com.amdp.android.entity.APIEntity;
import com.amdp.android.guihelpers.EntityListActivity;
import com.amdp.android.guihelpers.R;
import com.amdp.android.survey.entities.Survey;
import com.amdp.android.survey.entities.SurveyBLL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arley on 9/24/16.
 */
public class SurveysListActivity extends EntityListActivity {
    private List<Survey> surveys;

    protected String surveysTitle = "";
    protected String surveysApplyToId = "";


    @Override
    public void onResume(){
        super.onResume();


        if(TextUtils.isEmpty(surveysApplyToId)){
            surveys = SurveyBLL.getInstance().getSurveys();
        }else{
            surveys = SurveyBLL.getInstance().getSurveysToApply(surveysApplyToId);
        }


        ArrayList<APIEntity> vItems= new ArrayList<APIEntity>();
        for(Survey survey : surveys){
            vItems.add(survey);
        }
        fillList(vItems);

        //android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();
        String title = surveysTitle;
        if (surveysTitle.isEmpty()){
            title = getResources().getString(R.string.surveys_tile);
        }


        actionBar.setTitle(title);

        //getSupportActionBar().show();

    }


    public void onEntityClick(APIEntity apiEntity){

        SurveyBLL.getInstance().setSelectedSurvey((Survey)apiEntity);
        Intent intent = new Intent(this, QuestionsByOneForm.class);
        startActivity(intent);
    }


}
