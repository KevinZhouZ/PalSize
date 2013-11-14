package com.hiisniper.sizer.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.hiisniper.sizer.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by developer on 1/11/2013.
 */
public class ImageSelectActivity extends Activity {
    public static String LISTVIEW_ROW_NUM_KEY = "listRowNumKey";
    public static String LISTVIEW_IMAGE_TYPE_KEY = "listImageTypeKey";
    public static String LISTVIEW_IMAGE_URI_KEY = "listImageUriKey";
    public static String LISTVIEW_IMAGE_CHOOSER = "listImageChooer";

    public static String LISTVIEW_IMAGE_TYPE_BRAND = "listImageType_Brand";
    public static String LISTVIEW_IMAGE_TYPE_PRODUCT = "listImageType_Product";
    public static final int REQUEST_CODE_GALLERY = 100;
    public static final int REQUEST_CODE_CAMERA = 200;

    public static final String CHOOSE_FROM_GALLERY = "chooseFromGallery";
    public static final String CHOOSE_FROM_CAMERA = "chooseFromCamera";

    private static final String FORMAT = "yyyyMMdd_HHmmss";
    private int mRowNum;
    private String mImageType;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_select);

        if (getIntent().hasExtra(LISTVIEW_ROW_NUM_KEY)) {
            mRowNum = getIntent().getIntExtra(LISTVIEW_ROW_NUM_KEY, 0);
        }

        if (getIntent().hasExtra(LISTVIEW_IMAGE_TYPE_KEY)) {
            mImageType = getIntent().getStringExtra(LISTVIEW_IMAGE_TYPE_KEY);
        }

        if (getIntent().hasExtra(LISTVIEW_IMAGE_URI_KEY)) {
            String uri = getIntent().getStringExtra(LISTVIEW_IMAGE_URI_KEY);
            if(uri != null) {
                mImageUri = Uri.parse(uri);
            }

        }

        setupViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (resultCode == RESULT_OK) {
                data.putExtra(LISTVIEW_ROW_NUM_KEY, mRowNum);
                data.putExtra(LISTVIEW_IMAGE_TYPE_KEY, mImageType);
                data.putExtra(LISTVIEW_IMAGE_CHOOSER, CHOOSE_FROM_GALLERY);
                setResult(RESULT_OK, data);
                finish();

            }
        } else {
            if (resultCode == RESULT_OK) {
                if(data == null) {
                    data = new Intent();
                }
                data.putExtra(LISTVIEW_ROW_NUM_KEY, mRowNum);
                data.putExtra(LISTVIEW_IMAGE_TYPE_KEY, mImageType);
                data.putExtra(LISTVIEW_IMAGE_CHOOSER, CHOOSE_FROM_CAMERA);
                data.putExtra(MediaStore.EXTRA_OUTPUT,
                        mImageUri);
                setResult(RESULT_OK, data);
                finish();
                //selectedImagePath = getRealPathFromURI(selectedImageUri);
            }
        }
    }

    private void setupViews() {
        //image
        ImageView imageView = (ImageView) findViewById(R.id.image_select_image);
        if(mImageUri != null) {
            imageView.setImageURI(mImageUri);
        }
        //pick an image from gallery
        findViewById(R.id.image_select_btn_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickerIntent = new Intent(Intent.ACTION_PICK);
                pickerIntent.setType("image/*");
                startActivityForResult(pickerIntent, REQUEST_CODE_GALLERY);
            }
        });

        findViewById(R.id.image_select_btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                // request code
//                File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");


                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat(FORMAT, Locale.US);
                String time = sdf.format(date);
                // Create parameters for Intent with filename
                String fileName = "SIZER_" + time;
                ContentValues values = new ContentValues();

                values.put(MediaStore.Images.Media.TITLE, fileName);

                values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                mImageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        mImageUri);
                startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
            }
        });
    }
}
