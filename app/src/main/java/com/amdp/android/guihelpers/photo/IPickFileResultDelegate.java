package com.amdp.android.guihelpers.photo;

/**
 * Created by arley on 1/13/17.
 */

public interface IPickFileResultDelegate {
    void showFileChooser();
    String getPickedFileUUID();
}
