package com.hiisniper.sizer.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.hiisniper.sizer.R;
import com.hiisniper.sizer.provider.SizerProvider;

/**
 * Created by developer on 28/10/2013.
 */
public class Global {

    // Setting preference
    public static final String SETTING_STORE_NAME = "Settings";

    public static final String SETTING_ITEM_IS_FIRST_TIME_OPEN_APP = "isFirstTimeOpenApp";  // Boolean

    // Get head content uri
    public static Uri getHeadContentURI(Context context) {
        return Uri.parse("content://"
            + context.getString(R.string.database_authority_name) + "/" + SizerProvider.HEAD_TABLE_NAME);
    }

    // Get head content uri
    public static Uri getUpperContentURI(Context context) {
        return Uri.parse("content://"
            + context.getString(R.string.database_authority_name) + "/" + SizerProvider.UPPER_TABLE_NAME);
    }

    // Get head content uri
    public static Uri getLowerContentURI(Context context) {
        return Uri.parse("content://"
            + context.getString(R.string.database_authority_name) + "/" + SizerProvider.LOWER_TABLE_NAME);
    }

    // Get head content uri
    public static Uri getShoesContentURI(Context context) {
        return Uri.parse("content://"
            + context.getString(R.string.database_authority_name) + "/" + SizerProvider.SHOES_TABLE_NAME);
    }

    // Setting
    public static boolean getIsFirstTimeOpenApp(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Global.SETTING_STORE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(Global.SETTING_ITEM_IS_FIRST_TIME_OPEN_APP, true);
    }

    public static void setIsFirstTimeOpenApp(Context context, boolean isFirstTimeOpenApp) {
        SharedPreferences settings = context.getSharedPreferences(Global.SETTING_STORE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Global.SETTING_ITEM_IS_FIRST_TIME_OPEN_APP, isFirstTimeOpenApp);
        editor.commit();
    }
}
