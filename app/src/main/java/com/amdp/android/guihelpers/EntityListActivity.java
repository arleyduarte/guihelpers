package com.amdp.android.guihelpers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.amdp.android.entity.APIEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arley on 10/17/16.
 */

public class EntityListActivity extends AppCompatActivity {

    private List<APIEntity> items;
    private List<String> listValues;
    private ListView listView = null;

    protected ActionBar actionBar;
    protected EditText inputSearch;
    protected ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.list_activity);

        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        inputSearch = (EditText) findViewById(R.id.input_search);
    }

    public void fillList(final ArrayList<APIEntity> vItems){

        items = vItems;
        listValues = new ArrayList<String>();
        for(APIEntity item : items){
            listValues.add(item.getName());
        }

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);



        listView= (ListView)findViewById(R.id.listview);
        adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listValues);
        // assign the list adapter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        APIEntity apiEntity = vItems.get(position);
                        onEntityClick(apiEntity);
                    }
                }
        );


        // Once user enters a new data in EditText we need to get the text from
        // it and passing it to array adapter filter.
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                // When user changed the Text
                EntityListActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    public void onEntityClick(APIEntity apiEntity){


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
