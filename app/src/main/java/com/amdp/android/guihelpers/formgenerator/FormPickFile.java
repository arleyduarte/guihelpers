package com.amdp.android.guihelpers.formgenerator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amdp.android.guihelpers.R;
import com.amdp.android.guihelpers.photo.ContextPicker;


public class FormPickFile extends FormWidget
{
	protected TextView _label;
	protected ImageButton _input;
    protected  String value;

	public FormPickFile(Context context, String property ) {
        super(context, property);

        _label = new TextView(context);
        _label.setText(getDisplayText());
        _label.setLayoutParams(FormActivity.defaultLayoutParams);

        _input = new ImageButton(context);

                _input.setImageResource(R.mipmap.attach_file);
        _input.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


        _input.setBackgroundColor(Color.WHITE);


        _input.setLayoutParams(FormActivity.defaultLayoutParams);
        //_input.setImeOptions(EditorInfo.IME_ACTION_DONE);

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


        _input.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContextPicker.getInstance().getPickFileResultDelegate().showFileChooser();
            }
        });

        _layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

    }









	
	@Override
	public String getValue(){
		return ContextPicker.getInstance().getPickFileResultDelegate().getPickedFileUUID();
	}
	
	@Override
	public void setValue( String value ) {
		this.value = value;
	}
	
	@Override 
	public void setHint( String value ){

	}
}
