package com.joany.contentprovidersample;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by joany on 2016/8/17.
 */
public class UserProvider extends ContentProvider {

    private SQLiteDatabase db;
    private DBOpenHelper dbOpenHelper;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(User.AUTHORITY,User.ITEM,User.ITEM_TYPE);
        uriMatcher.addURI(User.AUTHORITY,User.ITEM_ID,User.ITEM_ID_TYPE);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbOpenHelper = new DBOpenHelper(context,DBOpenHelper.DB_NAME,null,DBOpenHelper.DB_VERSION);
        db = dbOpenHelper.getWritableDatabase();
        if(db == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DBOpenHelper.DB_TABLE);
        switch (uriMatcher.match(uri)) {
            case User.ITEM_TYPE:
                break;
            case User.ITEM_ID_TYPE:
                qb.appendWhere(User.KEY_ID+"="+uri.getPathSegments().get(1));
                break;
            default:
                break;
        }
        Cursor cursor = qb.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case User.ITEM_TYPE:
                return User.CONTENT_ITEM;
            case User.ITEM_ID_TYPE:
                return User.CONTENT_ITEM_ID;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long id = db.insert(DBOpenHelper.DB_TABLE,null,contentValues);
        if(id>0) {
            Uri newUri = ContentUris.withAppendedId(User.CONTENR_URI,id);
            getContext().getContentResolver().notifyChange(newUri,null);
            return newUri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case User.ITEM_TYPE:
                count = db.delete(DBOpenHelper.DB_TABLE,selection,selectionArgs);
                break;
            case User.ITEM_ID_TYPE:
                count = db.delete(DBOpenHelper.DB_TABLE,
                        User.KEY_ID+"="+uri.getPathSegments().get(1),selectionArgs);
                break;
            default:
                break;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case User.ITEM_TYPE:
                count = db.update(DBOpenHelper.DB_TABLE,contentValues,selection,selectionArgs);
                break;
            case User.ITEM_ID_TYPE:
                count = db.update(DBOpenHelper.DB_TABLE,contentValues,
                        User.KEY_ID+"="+uri.getPathSegments().get(1),selectionArgs);
                break;
            default:
                break;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }
}
