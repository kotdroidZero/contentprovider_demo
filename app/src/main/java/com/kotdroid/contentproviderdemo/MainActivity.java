package com.kotdroid.contentproviderdemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etAge) EditText etAge;

    ContentResolver mContentResolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mContentResolver = getContentResolver();
    }

    @OnClick(R.id.btnSubmit)
    public void save(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                Data data = new Data(etName.getText().toString().trim(), etAge.getText().toString().trim());
                ContentValues contentValues = new ContentValues();
                contentValues.put(Util.AGE, data.age);
                contentValues.put(Util.NAME, data.name);
                Uri uri = mContentResolver.insert(Util.USER_URI, contentValues);
                Toast.makeText(this, "the data inserted succesfully with id:" + uri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
