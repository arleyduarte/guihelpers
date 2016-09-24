/*******************************************************************************
 * Copyright (c) 2014. Zyght
 * All rights reserved. 
 *
 ******************************************************************************/

package com.amdp.android.guihelpers.photo;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.amdp.android.guihelpers.R;


/**
 * Created by Arley Mauricio Duarte
 */
public class PhotoManager {

    public static final int REQUEST_PICK_IMAGE = 1;
    public static final int REQUEST_TAKE_PHOTO = 11;

    private static PhotoManager ourInstance = new PhotoManager();

    private PhotoManager() {
    }



    public static PhotoManager getInstance() {
        return ourInstance;
    }

    public  void  displaySelectImage(final Context context, final IPhotoResultDelegate resultDelegate) {

        LayoutInflater factory = LayoutInflater.from(context);
        final View dialogView = factory.inflate(R.layout.close_survey_origin, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(dialogView);
        Button buttonCamera = (Button) dialogView.findViewById(R.id.buttonCamera);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                                resultDelegate.takePhoto();

                                            }
                                        }
        );
        Button buttonGallery = (Button) dialogView.findViewById(R.id.buttonGallery);
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                resultDelegate.takeImageFromSD();

            }
        });

        dialog.show();
    }



}
