package com.amdp.android.guihelpers.photo;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;


/**
 * Create a Image for the Alerts
 *
 * @author Arley Mauricio Duarte Palomino
 */
public class ImageBuilder {

    private static final String TAG = ImageBuilder.class.getSimpleName();
    private static final int IMAGE_QUALITY = 30;
    private static final int THUMBNAIL_SIZE = 200;

    private static final String FORMAT = ".jpg";
    private static Bitmap bitmapPhoto;

    public static File createImageFile() throws IOException {
        File storageDir = Environment.getExternalStorageDirectory();

        return File.createTempFile(getImageName(), /* prefix */
                FORMAT, /* suffix */
                storageDir /* directory */
        );
    }

    public static void galleryAddPic(Context context, File imageFile) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imageFile.getAbsoluteFile().toString());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * Create a Image from Uri
     *
     * @return absolute image path
     */
    public static ImageInfo createImageFile(Context context, Uri imgUri) {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setImageName(getImageName());


        try {
            final BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
            sizeOptions.inJustDecodeBounds = false;
            //The new size we want to scale to
            sizeOptions.inPreferredConfig = Bitmap.Config.RGB_565;
            sizeOptions.inSampleSize = 4;

            bitmapPhoto = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imgUri), null, sizeOptions);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();


            File photoIntent = new File(Environment.getExternalStorageDirectory(), imageInfo.getImageName());
            photoIntent.createNewFile();
            imageInfo.setAbsolutePath(photoIntent.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(photoIntent);




            bitmapPhoto = rotateBitmap(bitmapPhoto, getRotationAngle(imgUri));
            bitmapPhoto.compress(CompressFormat.JPEG, IMAGE_QUALITY, bos);


            byte[] bitmapdata = bos.toByteArray();
            fos.write(bitmapdata);
            fos.close();


        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Error");
        }catch (OutOfMemoryError ex) {
            Log.e(TAG, "We have no memory to rotate. Return the original bitmap.");
        }
        return imageInfo;
    }


    private static Bitmap rotateBitmap(Bitmap b, float degrees) {
        Log.d(TAG, "RotateBitmap: " + degrees);
        Matrix m = new Matrix();
        if (degrees != 0) {
            // clockwise
            m.postRotate(degrees, (float) b.getWidth() / 2,
                    (float) b.getHeight() / 2);
        }

        try {

            BitmapFactory.Options opts=new BitmapFactory.Options();
            opts.inDither=false;
            opts.inSampleSize = 16;
            opts.inPurgeable=true;
            opts.inInputShareable=true;
            opts.inTempStorage=new byte[16 * 1024];

            Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),b.getHeight(), m, true);
            if (b != b2) {
                b.recycle();
                b = b2;
            }
        } catch (OutOfMemoryError ex) {
            Log.e(TAG, "We have no memory to rotate. Return the original bitmap.");
        }
        return b;
    }

    private static int getRotationAngle(Uri imgUri) {
        int angle = 0;
        try {
            ExifInterface exif = new ExifInterface(imgUri.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                angle = 90;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                angle = 180;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                angle = 270;
            }

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return angle;
    }


    private static String getImageName() {
        return "AN_"+UUID.randomUUID().toString().replaceAll("-", "") + FORMAT;
    }


    public static void setThumbnailImage(String imagePath, ImageView imageButtonTemp) {

        int thumbnailWidth = THUMBNAIL_SIZE;
        int thumbnailHeight = THUMBNAIL_SIZE;

        if(imageButtonTemp != null && imageButtonTemp.getWidth() !=0){
            thumbnailWidth = imageButtonTemp.getWidth();
            thumbnailHeight = imageButtonTemp.getHeight();
        }
        setThumbnailImage(imagePath, imageButtonTemp, thumbnailWidth, thumbnailHeight);
    }

    public static void setThumbnailImage(String imagePath, ImageView imageButtonTemp, int width, int height) {
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            try {
                bitmapPhoto = BitmapFactory.decodeStream(new FileInputStream(imgFile));
                bitmapPhoto = Bitmap.createScaledBitmap(bitmapPhoto, width, height, false);
                imageButtonTemp.setImageBitmap(bitmapPhoto);
            } catch (FileNotFoundException e) {
                Log.e(TAG, e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Error setThumbnailImage");
            }catch (OutOfMemoryError ex) {
                Log.e(TAG, "We have no memory to rotate. Return the original bitmap.");
            }
        }
    }



    private static String getLocalPath(String remotePath){
        File storageDir = Environment.getExternalStorageDirectory();

        String localPath = "";

        if(remotePath.contains("/")){
            int lastPosition = remotePath.lastIndexOf("/");
            String name = remotePath.substring(lastPosition, remotePath.length());
            localPath = storageDir.getPath()+name;
        }
        return localPath;
    }




    public static Bitmap  getThumbnailImage(String imagePath) {

        Bitmap bitmapPhotoL = null;
        File imgFile = new File(imagePath);


        if (imgFile.exists()) {
            try {
                bitmapPhotoL = BitmapFactory.decodeStream(new FileInputStream(imgFile));
                bitmapPhotoL = Bitmap.createScaledBitmap(bitmapPhoto, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);

            } catch (FileNotFoundException e) {
                Log.e(TAG, e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Error setThumbnailImage");
            }catch (OutOfMemoryError ex) {
                Log.e(TAG, "We have no memory to rotate. Return the original bitmap.");
            }
        }

        return bitmapPhotoL;
    }









}