package com.joany.titanicsample;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;

import com.joany.librarytitanic.Titanic;
import com.joany.librarytitanic.TitanicTextView;

public class TitanicActivity extends Activity {

    private TitanicTextView titanicTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titanic);
        initData();
        intView();
    }

    private void initData() {
        titanicTextView = (TitanicTextView) findViewById(R.id.tv);
    }

    private void intView() {
        titanicTextView.setTypeface(Typeface.createFromAsset(this.getAssets(),"Satisfy-Regular.ttf"));
        new Titanic().start(titanicTextView);
    }
}
