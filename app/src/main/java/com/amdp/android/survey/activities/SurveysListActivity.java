package com.amdp.android.survey.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amdp.android.entity.APIEntity;
import com.amdp.android.entity.BasicEntityBLL;
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




    @Override
    public void onResume(){
        super.onResume();

        surveys = SurveyBLL.getInstance().getSurveys();
        ArrayList<APIEntity> vItems= new ArrayList<APIEntity>();
        for(Survey survey : surveys){
            vItems.add(survey);
        }
        fillList(vItems);

        //android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle("Instituciones Educativas");

        //getSupportActionBar().show();

    }


    public void onEntityClick(APIEntity apiEntity){

        SurveyBLL.getInstance().setSelectedSurvey((Survey)apiEntity);
        Intent intent = new Intent(this, QuestionsByOneForm.class);
        startActivity(intent);
    }


}
