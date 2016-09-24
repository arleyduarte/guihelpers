package com.amdp.android.guihelpers.formgenerator;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.amdp.android.guihelpers.R;


public class FormEditText extends FormWidget
{
	protected TextView _label;
	protected EditText _input;
	
	public FormEditText( Context context, String property ) {
        super(context, property);

        _label = new TextView(context);
        _label.setText(getDisplayText());
        _label.setLayoutParams(FormActivity.defaultLayoutParams);

        _input = new EditText(context);
        _input.setLayoutParams(FormActivity.defaultLayoutParams);
        _input.setImeOptions(EditorInfo.IME_ACTION_DONE);

        _layout.addView(_label);
        _layout.addView(_input);
        _input.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){

                    view.setBackgroundResource(R.drawable.focus_border_style);
                }
                else{
                   view.setBackgroundResource(R.drawable.lost_focus_style);
                }
            }
        });

    }








	
	@Override
	public String getValue(){
		return _input.getText().toString();
	}
	
	@Override
	public void setValue( String value ) {
		_input.setText( value );
	}
	
	@Override 
	public void setHint( String value ){
		_input.setHint( value );
	}
}
