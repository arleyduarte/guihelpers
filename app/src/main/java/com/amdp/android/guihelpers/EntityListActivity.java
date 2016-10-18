package com.amdp.android.guihelpers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amdp.android.entity.APIEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arley on 10/17/16.
 */

public class EntityListActivity extends Activity {

    private List<APIEntity> items;
    private List<String> listValues;
    private ListView listView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.list_activity);


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
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listValues);
        // assign the list adapter
        listView.setAdapter(itemsAdapter);

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

    }

    public void onEntityClick(APIEntity apiEntity){


    }
}
