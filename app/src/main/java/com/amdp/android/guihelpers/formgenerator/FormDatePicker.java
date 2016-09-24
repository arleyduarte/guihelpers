package com.amdp.android.guihelpers.formgenerator;

import android.content.Context;
import android.text.InputType;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class FormDatePicker extends FormWidget
{
	protected TextView _label;
	protected DatePicker _input;
	protected int _priority;

	public FormDatePicker(Context context, String property)
	{
		super( context, property );
		
		_label = new TextView( context );
		_label.setText( getDisplayText() );
		
		_input = new DatePicker( context );

		_input.setLayoutParams( FormActivity.defaultLayoutParams );
        _input.setCalendarViewShown(false);
		_layout.addView( _label );
		_layout.addView( _input );
	}

	public String getValue() {
		return _input.getYear() +"/"+_input.getMonth()+"/"+_input.getDayOfMonth();
	}

	public void setValue(String value) {

	}
	
	@Override 
	public void setHint( String value ){

	}
}
