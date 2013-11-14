package com.hiisniper.sizer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.hiisniper.sizer.R;
import com.hiisniper.sizer.activity.DataListActivity;
import com.hiisniper.sizer.activity.ImageSelectActivity;
import com.hiisniper.sizer.data.Product;
import com.hiisniper.sizer.provider.SizerProvider;

import java.util.ArrayList;

/**
 * Created by developer on 25/10/2013.
 */
public class DataListAdapter extends BaseAdapter{

    private DataListActivity mBaseActivity;
    private LayoutInflater mInflater;
    private ArrayList<String> mUuidList = new ArrayList<String>();
    private Uri mDbUri;

    public DataListAdapter(Activity activity, ArrayList<String> itemsList, Uri dbUri) {
        this.mBaseActivity = (DataListActivity)activity;
        mInflater = (LayoutInflater) activity.getBaseContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.mUuidList = itemsList;
        this.mDbUri = dbUri;
    }

    @Override
    public int getCount() {
        return mUuidList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUuidList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItemsList(ArrayList<String> itemsList) {
        this.mUuidList = itemsList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView==null) {
            convertView = mInflater.inflate(R.layout.data_list_page_item, null);
            holder = new ViewHolder();

            holder.brandImage = (ImageButton) convertView.findViewById(R.id.data_list_item_brand_image);
            holder.brandName = (TextView) convertView.findViewById(R.id.data_list_item_brand_name);
            holder.productImage = (ImageButton) convertView.findViewById(R.id.data_list_item_product_image);
            holder.productName = (TextView) convertView.findViewById(R.id.data_list_item_product_name);

            holder.size = (TextView) convertView.findViewById(R.id.data_list_item_size);
            holder.note = (TextView) convertView.findViewById(R.id.data_list_item_note);

            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }

        final Product product = getProductByUuid(mUuidList.get(position), this.mDbUri);

        // Set each view display
        /** Brand image */
        if (product.getBrandImageUri().isEmpty()) {
            holder.brandImage.setImageResource(R.drawable.item_add_btn);
        } else {
            holder.brandImage.setImageURI( Uri.parse(product.getBrandImageUri()) );
        }
        holder.brandImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the image selector screen
                Intent imageSelectorIntent = new Intent(mBaseActivity, ImageSelectActivity.class);
                if (!product.getBrandImageUri().isEmpty()) {
                    imageSelectorIntent.putExtra(ImageSelectActivity.LISTVIEW_IMAGE_URI_KEY, product.getBrandImageUri());
                }
                imageSelectorIntent.putExtra(ImageSelectActivity.LISTVIEW_ROW_NUM_KEY, position);
                imageSelectorIntent.putExtra(ImageSelectActivity.LISTVIEW_IMAGE_TYPE_KEY, ImageSelectActivity.LISTVIEW_IMAGE_TYPE_BRAND);
                mBaseActivity.startActivityForResult(imageSelectorIntent, mBaseActivity.REQUEST_CODE_SELECT_PHOTO);
            }
        });

        /** Brand name */
        holder.brandName.setText( product.getBrandName() );

        /** Product image */
        if (product.getProductImageUri().isEmpty()) {
            holder.productImage.setImageResource(R.drawable.item_add_btn);
        } else {
            holder.productImage.setImageURI( Uri.parse( product.getProductImageUri() ) );
        }
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the image selector screen
                Intent imageSelectorIntent = new Intent(mBaseActivity, ImageSelectActivity.class);
                if (!product.getProductImageUri().isEmpty()) {
                    imageSelectorIntent.putExtra(ImageSelectActivity.LISTVIEW_IMAGE_URI_KEY, product.getProductImageUri());
                }
                imageSelectorIntent.putExtra(ImageSelectActivity.LISTVIEW_ROW_NUM_KEY, position);
                imageSelectorIntent.putExtra(ImageSelectActivity.LISTVIEW_IMAGE_TYPE_KEY, ImageSelectActivity.LISTVIEW_IMAGE_TYPE_PRODUCT);
                mBaseActivity.startActivityForResult(imageSelectorIntent, mBaseActivity.REQUEST_CODE_SELECT_PHOTO);
            }
        });

        /** Product name */
        holder.productName.setText( product.getProductName() );

        /** Size */
        if (product.getSize().isEmpty()) {
            holder.size.setText( "00" );
        } else {
            holder.size.setText( product.getSize() );
        }

        /** Note */
        holder.note.setText( product.getNote() );

        return convertView;
    }

    private Product getProductByUuid(String uuid, Uri dbUri) {
        Product product = new Product();
        String[] projection = {SizerProvider.COLUMN_UUID};
        String selection = SizerProvider.COLUMN_UUID + " = '" + uuid + "'";

        Cursor c = mBaseActivity.getContentResolver().query(
            dbUri,
            projection,
            selection,
            null,
            null);

        if (c != null) {
            c.moveToFirst();
            int count = c.getCount();
            for (int i = 0; i < count; i++) {

                // Set product
                product.setUUID(uuid);
                product.setGroupName( c.getString(c.getColumnIndex(SizerProvider.COLUMN_GROUP_NAME)) );
                product.setBrandImageUri( c.getString(c.getColumnIndex(SizerProvider.COLUMN_BRAND_IMAGE_URI)) );
                product.setBrandName( c.getString(c.getColumnIndex(SizerProvider.COLUMN_BRAND_NAME)) );
                product.setProductImageUri( c.getString(c.getColumnIndex(SizerProvider.COLUMN_PRODUCT_IMAGE_URI)) );
                product.setProductName( c.getString(c.getColumnIndex(SizerProvider.COLUMN_PRODUCT_NAME)) );
                product.setSize( c.getString(c.getColumnIndex(SizerProvider.COLUMN_SIZE)) );
                product.setSizeCountryCode( c.getString(c.getColumnIndex(SizerProvider.COLUMN_SIZE_COUNTRY_CODE)) );
                product.setAddDate( c.getString(c.getColumnIndex(SizerProvider.COLUMN_ADD_DATE)) );
                product.setNote( c.getString(c.getColumnIndex(SizerProvider.COLUMN_NOTE)) );

                c.moveToNext();
            }
            c.close();
        }

        return product;
    }
}

class ViewHolder {
    ImageView brandImage;
    TextView brandName;
    ImageView productImage;
    TextView productName;
    TextView size;
    TextView note;
}
