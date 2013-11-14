package com.hiisniper.sizer.provider;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import com.hiisniper.sizer.R;
import com.hiisniper.sizer.global.Global;

/**
 * Created by developer on 28/10/2013.
 */
public class SizerProvider extends ContentProvider {
    private static final String DATABASE_NAME = "Sizer.db";
    private static final int DATABASE_VERSION = 1;

    // Column
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_UUID = "UUID";
    public static final String COLUMN_GROUP_NAME = "groupName";
    public static final String COLUMN_BRAND_IMAGE_URI = "brandImage";
    public static final String COLUMN_BRAND_NAME = "brandName";
    public static final String COLUMN_PRODUCT_IMAGE_URI = "productImage";
    public static final String COLUMN_PRODUCT_NAME = "productName";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_SIZE_COUNTRY_CODE = "sizeCountryCode";
    public static final String COLUMN_ADD_DATE = "addDate";
    public static final String COLUMN_NOTE = "note";

    // Table names
    public static final String HEAD_TABLE_NAME = "headTable";
    public static final String UPPER_TABLE_NAME = "upperTable";
    public static final String LOWER_TABLE_NAME = "lowerTable";
    public static final String SHOES_TABLE_NAME = "shoesTable";

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_UPPER = 1;
    private static final int TYPE_LOWER = 2;
    private static final int TYPE_SHOES = 3;

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase db;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private class DatabaseHelper extends SQLiteOpenHelper {
        private String[] mCategories;
        private String[] mSubCategories;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create the head table
            db.execSQL("CREATE TABLE " + HEAD_TABLE_NAME + " ("
                + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_UUID + " text, "
                + COLUMN_GROUP_NAME + " text, "
                + COLUMN_BRAND_IMAGE_URI + " text, "
                + COLUMN_BRAND_NAME + " text, "
                + COLUMN_PRODUCT_IMAGE_URI + " text, "
                + COLUMN_PRODUCT_NAME + " text, "
                + COLUMN_SIZE + " text, "
                + COLUMN_SIZE_COUNTRY_CODE + " text, "
                + COLUMN_ADD_DATE + " text, "
                + COLUMN_NOTE + " text);");

            // create the upper table
            db.execSQL("CREATE TABLE " + UPPER_TABLE_NAME + " ("
                + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_UUID + " text, "
                + COLUMN_GROUP_NAME + " text, "
                + COLUMN_BRAND_IMAGE_URI + " text, "
                + COLUMN_BRAND_NAME + " text, "
                + COLUMN_PRODUCT_IMAGE_URI + " text, "
                + COLUMN_PRODUCT_NAME + " text, "
                + COLUMN_SIZE + " text, "
                + COLUMN_SIZE_COUNTRY_CODE + " text, "
                + COLUMN_ADD_DATE + " text, "
                + COLUMN_NOTE + " text);");

            // create the lower table
            db.execSQL("CREATE TABLE " + LOWER_TABLE_NAME + " ("
                + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_UUID + " text, "
                + COLUMN_GROUP_NAME + " text, "
                + COLUMN_BRAND_IMAGE_URI + " text, "
                + COLUMN_BRAND_NAME + " text, "
                + COLUMN_PRODUCT_IMAGE_URI + " text, "
                + COLUMN_PRODUCT_NAME + " text, "
                + COLUMN_SIZE + " text, "
                + COLUMN_SIZE_COUNTRY_CODE + " text, "
                + COLUMN_ADD_DATE + " text, "
                + COLUMN_NOTE + " text);");

            // create the shoes table
            db.execSQL("CREATE TABLE " + SHOES_TABLE_NAME + " ("
                + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_UUID + " text, "
                + COLUMN_GROUP_NAME + " text, "
                + COLUMN_BRAND_IMAGE_URI + " text, "
                + COLUMN_BRAND_NAME + " text, "
                + COLUMN_PRODUCT_IMAGE_URI + " text, "
                + COLUMN_PRODUCT_NAME + " text, "
                + COLUMN_SIZE + " text, "
                + COLUMN_SIZE_COUNTRY_CODE + " text, "
                + COLUMN_ADD_DATE + " text, "
                + COLUMN_NOTE + " text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVertion, int newVertion) {
            // Create temp tables
            db.execSQL("ALTER TABLE " + HEAD_TABLE_NAME + " RENAME TO " + "temp_" + HEAD_TABLE_NAME);
            db.execSQL("ALTER TABLE " + UPPER_TABLE_NAME + " RENAME TO " + "temp_" + UPPER_TABLE_NAME);
            db.execSQL("ALTER TABLE " + LOWER_TABLE_NAME + " RENAME TO " + "temp_" + LOWER_TABLE_NAME);
            db.execSQL("ALTER TABLE " + SHOES_TABLE_NAME + " RENAME TO " + "temp_" + SHOES_TABLE_NAME);

            // create the head table
            db.execSQL("CREATE TABLE " + HEAD_TABLE_NAME + " ("
                + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_UUID + " text, "
                + COLUMN_GROUP_NAME + " text, "
                + COLUMN_BRAND_IMAGE_URI + " text, "
                + COLUMN_BRAND_NAME + " text, "
                + COLUMN_PRODUCT_IMAGE_URI + " text, "
                + COLUMN_PRODUCT_NAME + " text, "
                + COLUMN_SIZE + " text, "
                + COLUMN_SIZE_COUNTRY_CODE + " text, "
                + COLUMN_ADD_DATE + " text, "
                + COLUMN_NOTE + " text);");

            // create the upper table
            db.execSQL("CREATE TABLE " + UPPER_TABLE_NAME + " ("
                + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_UUID + " text, "
                + COLUMN_GROUP_NAME + " text, "
                + COLUMN_BRAND_IMAGE_URI + " text, "
                + COLUMN_BRAND_NAME + " text, "
                + COLUMN_PRODUCT_IMAGE_URI + " text, "
                + COLUMN_PRODUCT_NAME + " text, "
                + COLUMN_SIZE + " text, "
                + COLUMN_SIZE_COUNTRY_CODE + " text, "
                + COLUMN_ADD_DATE + " text, "
                + COLUMN_NOTE + " text);");

            // create the lower table
            db.execSQL("CREATE TABLE " + LOWER_TABLE_NAME + " ("
                + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_UUID + " text, "
                + COLUMN_GROUP_NAME + " text, "
                + COLUMN_BRAND_IMAGE_URI + " text, "
                + COLUMN_BRAND_NAME + " text, "
                + COLUMN_PRODUCT_IMAGE_URI + " text, "
                + COLUMN_PRODUCT_NAME + " text, "
                + COLUMN_SIZE + " text, "
                + COLUMN_SIZE_COUNTRY_CODE + " text, "
                + COLUMN_ADD_DATE + " text, "
                + COLUMN_NOTE + " text);");

            // create the shoes table
            db.execSQL("CREATE TABLE " + SHOES_TABLE_NAME + " ("
                + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_UUID + " text, "
                + COLUMN_GROUP_NAME + " text, "
                + COLUMN_BRAND_IMAGE_URI + " text, "
                + COLUMN_BRAND_NAME + " text, "
                + COLUMN_PRODUCT_IMAGE_URI + " text, "
                + COLUMN_PRODUCT_NAME + " text, "
                + COLUMN_SIZE + " text, "
                + COLUMN_SIZE_COUNTRY_CODE + " text, "
                + COLUMN_ADD_DATE + " text, "
                + COLUMN_NOTE + " text);");

            // Transfer data
            transferDataToTable(db);

            // Drop the temp tables
            db.execSQL("DROP TABLE IF EXISTS " + "temp_" + HEAD_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + "temp_" + UPPER_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + "temp_" + LOWER_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + "temp_" + SHOES_TABLE_NAME);
        }

        private void transferDataToTable(SQLiteDatabase db) {
            // deleted head table
            db.execSQL("INSERT INTO "
                + HEAD_TABLE_NAME + " (" + COLUMN_GROUP_NAME + "," + COLUMN_UUID + "," + COLUMN_BRAND_IMAGE_URI + ","
                                        + COLUMN_BRAND_NAME + "," + COLUMN_PRODUCT_IMAGE_URI + ","
                                        + COLUMN_PRODUCT_NAME + "," + COLUMN_SIZE + ","
                                        + COLUMN_SIZE_COUNTRY_CODE + "," + COLUMN_ADD_DATE + ","
                                        + COLUMN_NOTE + ")"
                + " SELECT " + COLUMN_GROUP_NAME + "," + COLUMN_UUID + "," + COLUMN_BRAND_IMAGE_URI + ","
                             + COLUMN_BRAND_NAME + "," + COLUMN_PRODUCT_IMAGE_URI + ","
                             + COLUMN_PRODUCT_NAME + "," + COLUMN_SIZE + ","
                             + COLUMN_SIZE_COUNTRY_CODE + "," + COLUMN_ADD_DATE + ","
                             + COLUMN_NOTE
                + " FROM temp_" + HEAD_TABLE_NAME);

            // deleted head table
            db.execSQL("INSERT INTO "
                + UPPER_TABLE_NAME + " (" + COLUMN_GROUP_NAME + "," + COLUMN_UUID + "," + COLUMN_BRAND_IMAGE_URI + ","
                                        + COLUMN_BRAND_NAME + "," + COLUMN_PRODUCT_IMAGE_URI + ","
                                        + COLUMN_PRODUCT_NAME + "," + COLUMN_SIZE + ","
                                        + COLUMN_SIZE_COUNTRY_CODE + "," + COLUMN_ADD_DATE + ","
                                        + COLUMN_NOTE + ")"
                + " SELECT " + COLUMN_GROUP_NAME + "," + COLUMN_UUID + "," + COLUMN_BRAND_IMAGE_URI + ","
                            + COLUMN_BRAND_NAME + "," + COLUMN_PRODUCT_IMAGE_URI + ","
                            + COLUMN_PRODUCT_NAME + "," + COLUMN_SIZE + ","
                            + COLUMN_SIZE_COUNTRY_CODE + "," + COLUMN_ADD_DATE + ","
                            + COLUMN_NOTE
                + " FROM temp_" + UPPER_TABLE_NAME);

            // deleted head table
            db.execSQL("INSERT INTO "
                + LOWER_TABLE_NAME + " (" + COLUMN_GROUP_NAME + "," + COLUMN_UUID + "," + COLUMN_BRAND_IMAGE_URI + ","
                                        + COLUMN_BRAND_NAME + "," + COLUMN_PRODUCT_IMAGE_URI + ","
                                        + COLUMN_PRODUCT_NAME + "," + COLUMN_SIZE + ","
                                        + COLUMN_SIZE_COUNTRY_CODE + "," + COLUMN_ADD_DATE + ","
                                        + COLUMN_NOTE + ")"
                + " SELECT " + COLUMN_GROUP_NAME + "," + COLUMN_UUID + "," + COLUMN_BRAND_IMAGE_URI + ","
                            + COLUMN_BRAND_NAME + "," + COLUMN_PRODUCT_IMAGE_URI + ","
                            + COLUMN_PRODUCT_NAME + "," + COLUMN_SIZE + ","
                            + COLUMN_SIZE_COUNTRY_CODE + "," + COLUMN_ADD_DATE + ","
                            + COLUMN_NOTE
                + " FROM temp_" + LOWER_TABLE_NAME);

            // deleted head table
            db.execSQL("INSERT INTO "
                + SHOES_TABLE_NAME + " (" + COLUMN_GROUP_NAME + "," + COLUMN_UUID + "," + COLUMN_BRAND_IMAGE_URI + ","
                                        + COLUMN_BRAND_NAME + "," + COLUMN_PRODUCT_IMAGE_URI + ","
                                        + COLUMN_PRODUCT_NAME + "," + COLUMN_SIZE + ","
                                        + COLUMN_SIZE_COUNTRY_CODE + "," + COLUMN_ADD_DATE + ","
                                        + COLUMN_NOTE + ")"
                + " SELECT " + COLUMN_GROUP_NAME + "," + COLUMN_UUID + "," + COLUMN_BRAND_IMAGE_URI + ","
                            + COLUMN_BRAND_NAME + "," + COLUMN_PRODUCT_IMAGE_URI + ","
                            + COLUMN_PRODUCT_NAME + "," + COLUMN_SIZE + ","
                            + COLUMN_SIZE_COUNTRY_CODE + "," + COLUMN_ADD_DATE + ","
                            + COLUMN_NOTE
                + " FROM temp_" + SHOES_TABLE_NAME);
        }
    }

    @Override
    public int delete(Uri uri, String whereClause, String[] whereArgs) {
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case TYPE_HEAD:
                return db.delete(HEAD_TABLE_NAME, whereClause, whereArgs);
            case TYPE_UPPER:
                return db.delete(UPPER_TABLE_NAME, whereClause, whereArgs);
            case TYPE_LOWER:
                return db.delete(LOWER_TABLE_NAME, whereClause, whereArgs);
            case TYPE_SHOES:
                return db.delete(SHOES_TABLE_NAME, whereClause, whereArgs);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = 0;
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case TYPE_HEAD:
                rowId = db.insert(HEAD_TABLE_NAME, null, values);
                if (rowId > 0) {
                    return ContentUris.withAppendedId(
                        Global.getHeadContentURI(getContext()), rowId);
                } else {
                    return null;
                }
            case TYPE_UPPER:
                rowId = db.insert(UPPER_TABLE_NAME, null, values);
                if (rowId > 0) {
                    return ContentUris.withAppendedId(
                        Global.getUpperContentURI(getContext()), rowId);
                } else {
                    return null;
                }
            case TYPE_LOWER:
                rowId = db.insert(LOWER_TABLE_NAME, null, values);
                if (rowId > 0) {
                    return ContentUris.withAppendedId(
                        Global.getLowerContentURI(getContext()), rowId);
                } else {
                    return null;
                }
            case TYPE_SHOES:
                rowId = db.insert(SHOES_TABLE_NAME, null, values);
                if (rowId > 0) {
                    return ContentUris.withAppendedId(
                        Global.getShoesContentURI(getContext()), rowId);
                } else {
                    return null;
                }
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        // return null;
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new DatabaseHelper(getContext());
        db = mDBHelper.getWritableDatabase();

        // Init URI Matcher
        sURIMatcher.addURI(getContext().getString(R.string.database_authority_name), HEAD_TABLE_NAME, TYPE_HEAD);
        sURIMatcher.addURI(getContext().getString(R.string.database_authority_name), UPPER_TABLE_NAME, TYPE_UPPER);
        sURIMatcher.addURI(getContext().getString(R.string.database_authority_name), LOWER_TABLE_NAME, TYPE_LOWER);
        sURIMatcher.addURI(getContext().getString(R.string.database_authority_name), SHOES_TABLE_NAME, TYPE_SHOES);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case TYPE_HEAD:
                return db.query(HEAD_TABLE_NAME, null, selection,
                    selectionArgs, null, null, sortOrder);

            case TYPE_UPPER:
                return db.query(UPPER_TABLE_NAME, null, selection,
                    selectionArgs, null, null, sortOrder);

            case TYPE_LOWER:
                return db.query(LOWER_TABLE_NAME, null, selection,
                    selectionArgs, null, null, sortOrder);

            case TYPE_SHOES:
                return db.query(SHOES_TABLE_NAME, null, selection,
                    selectionArgs, null, null, sortOrder);

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case TYPE_HEAD:
                return db.update(HEAD_TABLE_NAME, values, selection, selectionArgs);

            case TYPE_UPPER:
                return db.update(UPPER_TABLE_NAME, values, selection, selectionArgs);

            case TYPE_LOWER:
                return db.update(LOWER_TABLE_NAME, values, selection, selectionArgs);

            case TYPE_SHOES:
                return db.update(SHOES_TABLE_NAME, values, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }
}
