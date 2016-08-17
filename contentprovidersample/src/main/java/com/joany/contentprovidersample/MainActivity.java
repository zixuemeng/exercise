package com.joany.contentprovidersample;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "zixuemeng";
    private Button insert;
    private Button delete;
    private Button update;
    private Button query;
    private ContentResolver resolver;
    private ContentValues contentValues;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData(){
        insert = (Button) findViewById(R.id.insert);
        delete = (Button) findViewById(R.id.delete);
        update = (Button) findViewById(R.id.update);
        query = (Button) findViewById(R.id.query);
        resolver = getContentResolver();
    }

    private void initView(){
        insert.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        query.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insert:
                contentValues = new ContentValues();
                contentValues.put(User.KEY_NAME,"AB");
                contentValues.put(User.KEY_AGE,19);
                contentValues.put(User.KEY_HEIGHT,169.0);
                resolver.insert(User.CONTENR_URI, contentValues);
                break;
            case R.id.delete:
                //resolver.delete(User.CONTENR_URI,null,null);
                //uri = Uri.parse(User.CONTENT_URI_STRING+"/3");
                //resolver.delete(uri,null,null);
                resolver.delete(User.CONTENR_URI,User.KEY_ID+"=?",new String[]{"4"});
                break;
            case R.id.update:
                contentValues = new ContentValues();
                contentValues.put(User.KEY_NAME,"Joany");
                contentValues.put(User.KEY_AGE,18);
                contentValues.put(User.KEY_HEIGHT,168.0);
                uri = Uri.parse(User.CONTENT_URI_STRING+"/1");
                int result = resolver.update(uri,contentValues,null,null);
                Log.i(TAG,"update result:"+result);
                break;
            case R.id.query:
                Cursor cursor = resolver.query(User.CONTENR_URI,
                        new String[]{User.KEY_ID,User.KEY_NAME,User.KEY_AGE,User.KEY_HEIGHT},
                        null,null,null);
                if(cursor == null) {
                    Log.i(TAG,"table has no data");
                    break;
                }
                StringBuilder stringBuilder = new StringBuilder();
                if(cursor.moveToFirst()){
                    do{
                        stringBuilder.append("ID:"+cursor.getString(cursor.getColumnIndex(User.KEY_ID)));
                        stringBuilder.append("Name:"+cursor.getString(cursor.getColumnIndex(User.KEY_NAME)));
                        stringBuilder.append("Age:"+cursor.getInt(cursor.getColumnIndex(User.KEY_AGE)));
                        stringBuilder.append("Height:"+cursor.getFloat(cursor.getColumnIndex(User.KEY_HEIGHT)));
                    } while(cursor.moveToNext());
                    Log.i(TAG,stringBuilder.toString());
                }
                break;
            default:
                break;
        }
    }
}
