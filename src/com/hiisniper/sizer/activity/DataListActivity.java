package com.hiisniper.sizer.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hiisniper.sizer.R;
import com.hiisniper.sizer.adapter.DataListAdapter;
import com.hiisniper.sizer.data.Product;
import com.hiisniper.sizer.global.Global;
import com.hiisniper.sizer.provider.SizerProvider;
import com.hiisniper.sizer.view.CirclePageIndicator;

import java.util.*;

/**
 * Created by developer on 25/10/2013.
 */
public class DataListActivity extends Activity {

    public static final String OPEN_FROM_HEAD_KEY = "openFromHead";
    public static final String OPEN_FROM_UPPER_KEY = "openFromUpper";
    public static final String OPEN_FROM_LOWER_KEY = "openFromLower";
    public static final String OPEN_FROM_SHOES_KEY = "openFromShoes";

    public static final int REQUEST_CODE_SELECT_PHOTO = 100;

    private static final int DB_HEAD = 1;
    private static final int DB_UPPER = 2;
    private static final int DB_LOWER = 3;
    private static final int DB_SHOES = 4;


    private int mCurrentDbType = DB_HEAD;

    private LayoutInflater mInflater;

    private ArrayList<View> mPageViews = new ArrayList<View>();
    private HashMap<String, View> mListViewsWithGroupNameKey = new HashMap<String, View>();
    private HashMap<String, ArrayList<String>> mUuidListWithGroupNameKey = new HashMap<String, ArrayList<String>>();
    PageAdapter adapter;
    private DataListAdapter dataListAdapter;
    private ArrayList<String> uuidList;

    private Uri mCurDbUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_data_list);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);

        if (getIntent().hasExtra(OPEN_FROM_HEAD_KEY)) {
            mCurrentDbType = DB_HEAD;
        } else if (getIntent().hasExtra(OPEN_FROM_UPPER_KEY)) {
            mCurrentDbType = DB_UPPER;
        } else if (getIntent().hasExtra(OPEN_FROM_LOWER_KEY)) {
            mCurrentDbType = DB_LOWER;
        } else if (getIntent().hasExtra(OPEN_FROM_SHOES_KEY)) {
            mCurrentDbType = DB_SHOES;
        } else {
            mCurrentDbType = DB_HEAD;
        }

        mInflater = (LayoutInflater) getBaseContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        SetupViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                int rowNum = data.getIntExtra(ImageSelectActivity.LISTVIEW_ROW_NUM_KEY, 0);
                String type = data.getStringExtra(ImageSelectActivity.LISTVIEW_IMAGE_TYPE_KEY);
                String imagePath = null;
                if(ImageSelectActivity.CHOOSE_FROM_GALLERY.equals(data.getStringExtra(ImageSelectActivity.LISTVIEW_IMAGE_CHOOSER))){
                    Uri selectedPhoto = data.getData();
                    imagePath = selectedPhoto.toString();
                } else if(ImageSelectActivity.CHOOSE_FROM_CAMERA.equals(data.getStringExtra(ImageSelectActivity.LISTVIEW_IMAGE_CHOOSER))){
                    Uri cameraImageUri = (Uri) data.getExtras().get(MediaStore.EXTRA_OUTPUT);
                    imagePath = cameraImageUri.toString();
                }


                updateImage(rowNum, type, imagePath);

            }
        }
    }

    private void SetupViews() {
        SetupListViews();
    }

    private void SetupListViews() {

        getGroupFromDB();
    }

    private class PageAdapter extends PagerAdapter {
        private ArrayList<View> mViews;

        public PageAdapter(ArrayList<View> views) {
            mViews = views;
        }

        @Override
        public int getCount() {
            if (mViews != null) {
                return mViews.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // TODO Auto-generated method stub
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mViews.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            View view = mViews.get(position);
            ((ViewPager) container).addView(view, 0);
            return view;
        }
    }

    private int getGroupFromDB() {
        Uri uri = null;
        switch (mCurrentDbType) {
            case DB_HEAD:
                uri = Global.getHeadContentURI(this);
                break;
            case DB_UPPER:
                uri = Global.getUpperContentURI(this);
                break;
            case DB_LOWER:
                uri = Global.getLowerContentURI(this);
                break;
            case DB_SHOES:
                uri = Global.getShoesContentURI(this);
                break;
            default:
                uri = Global.getHeadContentURI(this);
                break;
        }

        mCurDbUri = uri;

        Cursor c = this.getContentResolver().query(
                mCurDbUri,
                null,
                null,
                null,
                null);

        if (c != null) {
            c.moveToFirst();
            int count = c.getCount();
            String prevGroupName = null;

            for (int i = 0; i < count; i++) {

                // Create first page
                if (prevGroupName == null) {
                    prevGroupName = c.getString(c.getColumnIndex(SizerProvider.COLUMN_GROUP_NAME));

                    View pageView = mInflater.inflate(R.layout.data_list_page_view, null);
                    final TextView groupNameTextView = (TextView) pageView.findViewById(R.id.data_list_page_view_group_name);
                    groupNameTextView.setText(prevGroupName);
                    ListView listView = (ListView) pageView.findViewById(R.id.data_list_page_list);

                    ImageButton addButton = (ImageButton) pageView.findViewById(R.id.data_list_page_view_add_btn);
                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String groupName = groupNameTextView.getText().toString();

                            // Open add product dialog
                            if (groupName != null) {
                                addProductToList(groupName);
                            }
                        }
                    });
                    mPageViews.add(pageView);
                    mListViewsWithGroupNameKey.put(prevGroupName, listView);
                    ArrayList<String> uuidList = new ArrayList<String>();
                    mUuidListWithGroupNameKey.put(prevGroupName, uuidList);
                }

                String curGroupName = c.getString(c.getColumnIndex(SizerProvider.COLUMN_GROUP_NAME));

                // Create other pages
                if (!curGroupName.equals(prevGroupName)) {
                    View pageView = mInflater.inflate(R.layout.data_list_page_view, null);
                    final TextView groupNameTextView = (TextView) pageView.findViewById(R.id.data_list_page_view_group_name);
                    groupNameTextView.setText(curGroupName);
                    ListView listView = (ListView) pageView.findViewById(R.id.data_list_page_list);

                    ImageButton addButton = (ImageButton) pageView.findViewById(R.id.data_list_page_view_add_btn);
                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String groupName = groupNameTextView.getText().toString();

                            // Open add product dialog
                            if (groupName != null) {
                                addProductToList(groupName);
                            }
                        }
                    });
                    mPageViews.add(pageView);
                    mListViewsWithGroupNameKey.put(curGroupName, listView);
                    ArrayList<String> uuidList = new ArrayList<String>();
                    mUuidListWithGroupNameKey.put(prevGroupName, uuidList);
                }

                // Create list of every page
                ArrayList<String> uuidList = mUuidListWithGroupNameKey.get(curGroupName);
                uuidList.add(c.getString(c.getColumnIndex(SizerProvider.COLUMN_UUID)));

                c.moveToNext();

                // Set previous group name
                prevGroupName = curGroupName;
            }

            // Set adapters
            Set<String> set = mListViewsWithGroupNameKey.keySet();
            Iterator<String> iter = set.iterator();
            while (iter.hasNext()) {
                String groupName = iter.next();
                ListView listView = (ListView) mListViewsWithGroupNameKey.get(groupName);
                uuidList = (ArrayList<String>) mUuidListWithGroupNameKey.get(groupName);
                dataListAdapter = new DataListAdapter(this, (ArrayList<String>) mUuidListWithGroupNameKey.get(groupName), mCurDbUri);

                listView.setAdapter(dataListAdapter);
            }

            // Set main views
            ViewPager mViewPager = (ViewPager) findViewById(R.id.ViewPager_welcome);
            CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
            adapter = new PageAdapter(mPageViews);
            mViewPager.setAdapter(adapter);
            indicator.setViewPager(mViewPager);

            c.close();
        }
        return 0;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPageViews != null) {
            // Release the list
            mPageViews.clear();
            mPageViews = null;
        }
        if (mListViewsWithGroupNameKey != null) {
            mListViewsWithGroupNameKey.clear();
            mListViewsWithGroupNameKey = null;
        }
        if (mUuidListWithGroupNameKey != null) {
            mUuidListWithGroupNameKey.clear();
            mUuidListWithGroupNameKey = null;
        }

    }

    private void addProductToList(String groupName) {
        Product product = new Product();

        product.setUUID(UUID.randomUUID().toString());
        product.setGroupName(groupName);
        product.setBrandImageUri("");
        product.setBrandName("Brand");
        product.setProductImageUri("");
        product.setProductName("Product");
        product.setSize("41");
        product.setSizeCountryCode("");
        product.setAddDate("");
        product.setNote("test");

        ContentValues values = new ContentValues();

        values.put(SizerProvider.COLUMN_UUID, product.getUUID());
        values.put(SizerProvider.COLUMN_GROUP_NAME, product.getGroupName());
        values.put(SizerProvider.COLUMN_BRAND_IMAGE_URI, product.getBrandImageUri());
        values.put(SizerProvider.COLUMN_BRAND_NAME, product.getBrandName());
        values.put(SizerProvider.COLUMN_PRODUCT_IMAGE_URI, product.getProductImageUri());
        values.put(SizerProvider.COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(SizerProvider.COLUMN_SIZE, product.getSize());
        values.put(SizerProvider.COLUMN_SIZE_COUNTRY_CODE, product.getSizeCountryCode());
        values.put(SizerProvider.COLUMN_ADD_DATE, product.getAddDate());
        values.put(SizerProvider.COLUMN_NOTE, product.getNote());

        getContentResolver().insert(mCurDbUri, values);

        // Add uuid to list
        uuidList = (ArrayList<String>) mUuidListWithGroupNameKey.get(groupName);
        uuidList.add(product.getUUID());

        // Set items list again
        dataListAdapter = (DataListAdapter) ((ListView) (mListViewsWithGroupNameKey.get(groupName))).getAdapter();
        dataListAdapter.setItemsList(uuidList);

        // Notify data set changed
        dataListAdapter.notifyDataSetChanged();
    }


    private void updateImage(int rowNum, String type, String imageUrl) {
        if(imageUrl == null) {
            return;
        }
        String uuid = uuidList.get(rowNum);

        Product product = new Product();

        Cursor c = getContentResolver().query(
                mCurDbUri,
                null,
                SizerProvider.COLUMN_UUID + " = '" + uuid + "'",
                null,
                null);

        if (c != null) {
            c.moveToFirst();
            int count = c.getCount();
            if (count > 0) {

                // Set product
                product.setUUID(uuid);
                product.setGroupName(c.getString(c.getColumnIndex(SizerProvider.COLUMN_GROUP_NAME)));


                product.setBrandName(c.getString(c.getColumnIndex(SizerProvider.COLUMN_BRAND_NAME)));
                product.setProductImageUri(c.getString(c.getColumnIndex(SizerProvider.COLUMN_PRODUCT_IMAGE_URI)));
                product.setProductName(c.getString(c.getColumnIndex(SizerProvider.COLUMN_PRODUCT_NAME)));
                product.setSize(c.getString(c.getColumnIndex(SizerProvider.COLUMN_SIZE)));
                product.setSizeCountryCode(c.getString(c.getColumnIndex(SizerProvider.COLUMN_SIZE_COUNTRY_CODE)));
                product.setAddDate(c.getString(c.getColumnIndex(SizerProvider.COLUMN_ADD_DATE)));
                product.setNote(c.getString(c.getColumnIndex(SizerProvider.COLUMN_NOTE)));
                if (type.equals(ImageSelectActivity.LISTVIEW_IMAGE_TYPE_BRAND)) {
                    product.setBrandImageUri(imageUrl);
                    product.setProductImageUri(c.getString(c.getColumnIndex(SizerProvider.COLUMN_PRODUCT_IMAGE_URI)));
                } else {
                    product.setBrandImageUri(c.getString(c.getColumnIndex(SizerProvider.COLUMN_BRAND_IMAGE_URI)));
                    product.setProductImageUri(imageUrl);
                }
                //c.moveToNext();
            }
            c.close();
        }

        ContentValues values = new ContentValues();

        values.put(SizerProvider.COLUMN_UUID, product.getUUID());
        values.put(SizerProvider.COLUMN_GROUP_NAME, product.getGroupName());
        values.put(SizerProvider.COLUMN_BRAND_IMAGE_URI, product.getBrandImageUri());
        values.put(SizerProvider.COLUMN_BRAND_NAME, product.getBrandName());
        values.put(SizerProvider.COLUMN_PRODUCT_IMAGE_URI, product.getProductImageUri());
        values.put(SizerProvider.COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(SizerProvider.COLUMN_SIZE, product.getSize());
        values.put(SizerProvider.COLUMN_SIZE_COUNTRY_CODE, product.getSizeCountryCode());
        values.put(SizerProvider.COLUMN_ADD_DATE, product.getAddDate());
        values.put(SizerProvider.COLUMN_NOTE, product.getNote());


        getContentResolver().update(mCurDbUri, values, SizerProvider.COLUMN_UUID + " = '" + uuid + "'", null);

        // Notify data set changed
        adapter.notifyDataSetChanged();
        dataListAdapter.notifyDataSetChanged();
    }
}
