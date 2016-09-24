package com.amdp.android.survey.activities;

/**
 * Created by arley on 9/24/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.amdp.android.guihelpers.R;
import com.amdp.android.survey.entities.Survey;


import java.util.List;

/**
 * Created by Arley Mauricio Duarte
 */
class QuestionListAdapter extends BaseAdapter {


    private final LayoutInflater mInflater;
    private final List<Survey> list;
    private Context context;

    public QuestionListAdapter(Context context, final List<Survey> list) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        CalendarPointHolder holder;
        Survey entity = list.get(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.qlistview, null);

            holder = new CalendarPointHolder();
            holder.title = (TextView) convertView.findViewById(R.id.listText);
            // holder.subTitle = (TextView) convertView.findViewById(R.id.sub_title);


            convertView.setTag(holder);
        } else {

            holder = (CalendarPointHolder) convertView.getTag();
        }

        if (entity != null) {


            holder.title.setText(entity.getName());
            // holder.subTitle.setText(subTitle);

        }

        return convertView;
    }


    class CalendarPointHolder {

        TextView title;
        // TextView subTitle;



    }


}