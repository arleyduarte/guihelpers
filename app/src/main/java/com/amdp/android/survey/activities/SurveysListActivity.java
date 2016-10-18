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

import com.amdp.android.guihelpers.R;
import com.amdp.android.survey.entities.Survey;
import com.amdp.android.survey.entities.SurveyBLL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arley on 9/24/16.
 */
public class SurveysListActivity extends AppCompatActivity {
    private List<Survey> surveys;
    private List<String> listValues;
    private ListView listView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.surveys_list_activity);

        //text = (TextView) findViewById(R.id.mainText);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        surveys = SurveyBLL.getInstance().getSurveys();
        listValues = new ArrayList<String>();
        for(Survey survey : surveys){
            listValues.add(survey.getName());
        }

        /*
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
*/

        QuestionListAdapter myAdapter = new QuestionListAdapter(getApplicationContext(), surveys);

        listView= (ListView)findViewById(R.id.listview);

        // assign the list adapter
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {

                        SurveyBLL.getInstance().setSelectedSurvey(surveys.get(position));
                        showNext(position);
                    }
                }
        );


    }

    private void showNext(int position){
        SurveyBLL.getInstance().setSelectedSurvey(surveys.get(position));
        Intent intent = new Intent(this, QuestionsByOneForm.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
