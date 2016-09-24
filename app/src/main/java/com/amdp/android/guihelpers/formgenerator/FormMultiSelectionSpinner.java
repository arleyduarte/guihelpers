package com.amdp.android.guihelpers.formgenerator;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FormMultiSelectionSpinner extends FormWidget
{
	protected JSONObject _options;
	protected TextView _label;
	protected MultiSelectionSpinner _spinner;
	protected Map<String, String> _propmap;
	protected ArrayAdapter<String> _adapter;

	public FormMultiSelectionSpinner(Context context, String property, JSONObject options)
	{
		super( context, property );
	
		_options = options;
		
		_label = new TextView( context );
		_label.setText( getDisplayText() );
		_label.setLayoutParams( FormActivity.defaultLayoutParams );
		
		_spinner = new MultiSelectionSpinner( context );
		_spinner.setLayoutParams( FormActivity.defaultLayoutParams );

		String p;
		String name;
		JSONArray propertyNames = options.names();
		
		_propmap = new HashMap<String, String>();
		_adapter = new ArrayAdapter<String>( context, android.R.layout.simple_spinner_item );
		_adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		//_spinner.setAdapter( _adapter );
		//_spinner.setSelection( 0 );

        ArrayList<String> lista = new ArrayList<String>();
		
		try{
			for( int i = 0; i < options.length(); i++ ) 
			{
				name =  propertyNames.getString(i);
				p = options.getString( name );

                lista.add(p);
				//_adapter.add( p );
				_propmap.put( p, name );
			}
		} catch( JSONException e){
			
		}

        lista.add("");
        Collections.sort(lista);

        for( int i = 0; i < lista.size(); i++ )
        {

            _adapter.add( lista.get(i) );

        }

        _spinner.setItems(lista);

        _layout.addView( _label );
		_layout.addView( _spinner );
	}
	
	@Override
	public String getValue() {
       return  _spinner.getSelectedItemsAsString();
        //return _propmap.get( _adapter.getItem( _spinner.getSelectedItemPosition() ) );
	}
	
	@Override
	public void setValue(String value)
	{
		try{
			String name;
			JSONArray names = _options.names();
			for( int i = 0; i < names.length(); i++ )
			{
				name = names.getString(i);
				
				if( name.equals(value) )
				{
					String item = _options.getString(name);
					//_spinner.setSelection( _adapter.getPosition(item) );
				}
			}
		} catch( JSONException e ){
			Log.i("Lykaion", e.getMessage() );
		}
	}
	
	@Override 
	public void setToggleHandler( FormActivity.FormWidgetToggleHandler handler )
	{
		super.setToggleHandler(handler);
		_spinner.setOnItemSelectedListener( new SelectionHandler( this ) );
	}
	
	class SelectionHandler implements AdapterView.OnItemSelectedListener 
	{
		protected FormWidget _widget;
		
		public SelectionHandler( FormWidget widget ){
			_widget = widget;
		}
		
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if( _handler != null ){
				_handler.toggle( _widget );
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
		
	}
}
