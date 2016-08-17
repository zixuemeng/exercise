package com.joany.contentprovidersample;

import android.net.Uri;

/**
 * Created by joany on 2016/8/17.
 */
public class User {
    public static final String AUTHORITY = "com.joany.contentprovidersample";
    public static final String ITEM = "user";
    public static final String ITEM_ID = "user/#";
    public static final String CONTENT_URI_STRING = "content://"+AUTHORITY+"/"+ITEM;
    public static final Uri CONTENR_URI = Uri.parse(CONTENT_URI_STRING);

    public static final int ITEM_TYPE = 0;
    public static final int ITEM_ID_TYPE = 1;

    public static final String CONTENT_ITEM = "vnd.android.cursor.dir/com.joany.contentprovidersample.user";
    public static final String CONTENT_ITEM_ID = "vnd.android.cursor.item/com.joany.contentprovidersample.user";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_AGE = "age";
    public static final String KEY_HEIGHT = "height";
}
