package com.amdp.android.guihelpers;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by arley on 12/16/16.
 */

public class MenuListActivity extends AppCompatActivity {
    protected ListView listView1;
    protected ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_list_activity);

        actionBar = this.getSupportActionBar();
        actionBar.setTitle(getString(R.string.menu));

    }


    public void fillList(String[] menuOptions, int[] drawableIds) {

        MenuListAdapter adapter =  new MenuListAdapter(this,  menuOptions, drawableIds);

        listView1 = (ListView)findViewById(R.id.list);
        listView1.setAdapter(adapter);
        this.listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                onEntityClick(position);
            }


        });


    }



    //----------Separar


    /*
    public void onResume(){
        super.onResume();

        String[] textString = {"Instituciones Educativas", "Reportar Alerta", "Alertas", "Reportes"};
        int[] drawableIds = {R.mipmap.school, R.mipmap.alert, R.mipmap.get_alerts,R.mipmap.reports};


        fillList(textString, drawableIds);



    }

    public void onEntityClick(int position) {
        Intent intent = null;

        switch (position){
            case 0:
                intent = new Intent(this, MESchoolList.class);
                break;
            case 1:
                intent = new Intent(this, MENewAlertActivity.class);
                break;
            case 2:
                intent = new Intent(this, MEAlertsList.class);
                break;
            case 3:
                intent = new Intent(this, MEReportList.class);
                break;
        }



        startActivity(intent);
    }
*/

    public void onEntityClick(int position) {

    }
}
