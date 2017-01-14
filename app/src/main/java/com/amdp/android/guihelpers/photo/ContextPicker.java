package com.amdp.android.guihelpers.photo;

/**
 * Created by arley on 1/13/17.
 */
public class ContextPicker {

    private IPhotoResultDelegate iPhotoResultDelegate;
    private  IPickFileResultDelegate iPickFileResultDelegate;



    private static ContextPicker ourInstance = new ContextPicker();

    public static ContextPicker getInstance() {
        return ourInstance;
    }

    private ContextPicker() {
    }

    public IPhotoResultDelegate getPhotoResultDelegate() {
        return iPhotoResultDelegate;
    }

    public void setPhotoResultDelegate(IPhotoResultDelegate iPhotoResultDelegate) {
        this.iPhotoResultDelegate = iPhotoResultDelegate;
    }

    public IPickFileResultDelegate getPickFileResultDelegate() {
        return iPickFileResultDelegate;
    }

    public void setPickFileResultDelegate(IPickFileResultDelegate iPickFileResultDelegate) {
        this.iPickFileResultDelegate = iPickFileResultDelegate;
    }
}
