package com.amdp.android.guihelpers.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


import com.amdp.android.entity.APIEntity;
import com.amdp.android.guihelpers.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Arley Mauricio Duarte
 */
public class DataAdapter {

    private static String[] getNames(ArrayList<APIEntity> vItems) {
        String[] names = new String[vItems.size()];
        int i = 0;
        for (APIEntity r : vItems) {
            names[i] = r.getName();
            i++;
        }
        return names;
    }

    private static String[] getNames(ArrayList<APIEntity> vItems, APIEntity defaultItem) {
        String[] names = new String[vItems.size() + 1];
        names[0] = defaultItem.getName();


        int i = 1;
        for (APIEntity r : vItems) {
            names[i] = r.getName();
            i++;
        }
        return names;
    }

    public static ArrayAdapter<String> getArrayAdapter(ArrayList<APIEntity> vItems, Context context) {
        return getArrayAdapter(vItems, null, context);
    }

    public static ArrayAdapter<String> getArrayAdapter(ArrayList<APIEntity> vItems, APIEntity defaultItem, Context context) {
        String[] names;
        if (defaultItem == null) {
            names = getNames(vItems);
        } else {
            names = getNames(vItems, defaultItem);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, names);


        //adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);

        //ArrayAdapter<String> adapter = new CustomArrayAdapter<String>(context, names);

        return adapter;
    }


    static class CustomArrayAdapter<T> extends ArrayAdapter<T>
    {
        public CustomArrayAdapter(Context ctx, T [] objects)
        {
            super(ctx, android.R.layout.simple_spinner_dropdown_item, objects);
        }

        //other constructors

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {
            View view = super.getView(position, convertView, parent);

            //we know that simple_spinner_item has android.R.id.text1 TextView:

        /* if(isDroidX) {*/
            TextView text = (TextView)view.findViewById(android.R.id.text1);
            text.setTextColor(Color.BLACK);//choose your color :)
        /*}*/

            return view;

        }
    }

    public static ArrayAdapter<CharSequence> getArrayAdapter(int textArrayResId, Context context) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                textArrayResId, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    public static String getEntityId(Spinner spinner, ArrayList<APIEntity> vItems) {
        String entityId = "";
        if (spinner.getSelectedItem() != null) {
            APIEntity apiEntity = findByName(spinner.getSelectedItem().toString(), vItems);
            if (apiEntity != null) {
                entityId = apiEntity.getEntityId();
            }
        }
        return entityId;
    }

    public static String getSpinnerName(Spinner spinner) {
        String name = "";

        if (spinner != null && spinner.getSelectedItem() != null && !spinner.getSelectedItem().toString().isEmpty()) {
            name = spinner.getSelectedItem().toString();
        }

        return name;
    }

    private static APIEntity findByName(String name, ArrayList<APIEntity> vItems) {
        APIEntity apiEntity = null;
        for (APIEntity aux : vItems) {
            if (aux.getName() != null && aux.getName().equals(name)) {
                apiEntity = aux;
            }
        }
        return apiEntity;
    }

    /**
     *
     * @param datePicker
     * @return a java.util.Date
     */
    public static java.util.Date getDateFromDatePicket(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();


        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    public static java.util.Date getDateFromDatePicket(DatePicker datePicker, TimePicker timePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, timePicker.getCurrentHour(), timePicker.getCurrentMinute());

        return calendar.getTime();
    }

}
