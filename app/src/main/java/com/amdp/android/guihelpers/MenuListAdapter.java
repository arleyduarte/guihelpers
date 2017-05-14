package com.amdp.android.guihelpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by arley on 12/16/16.
 */

public class MenuListAdapter extends BaseAdapter {
    private Context mContext;
    private String[]  Title;
    private int[] imge;

    public MenuListAdapter(Context context, String[] text1, int[] imageIds) {
        mContext = context;
        Title = text1;
        imge = imageIds;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return Title.length;
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View row;
        row = inflater.inflate(R.layout.menu_list_row, parent, false);
        TextView title;
        ImageView i1;
        i1 = (ImageView) row.findViewById(R.id.imgIcon);
        title = (TextView) row.findViewById(R.id.txtTitle);
        title.setText(Title[position]);
        i1.setImageResource(imge[position]);

        return (row);
    }
}
