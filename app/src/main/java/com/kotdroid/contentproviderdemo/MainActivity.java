package com.kotdroid.contentproviderdemo;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etAge) EditText etAge;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private DataAdapter mDataAdapter;
    private ContentResolver mContentResolver;
    private ContentValues contentValues;

    private String[] mProjection = new String[]{Util.AGE, Util.NAME, Util.ID};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        findViewById(R.id.flContainerMain).setVisibility(View.GONE);
        mContentResolver = getContentResolver();
        contentValues = new ContentValues();
        mDataAdapter = new DataAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mDataAdapter);

        getLoaderManager().initLoader(1, null, MainActivity.this);

    }

    @OnClick({R.id.btnSubmit, R.id.btnOpenFragment, R.id.btnIPCRemoteBinding})
    public void save(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                Data data = new Data();
                data.setName(etName.getText().toString().trim());
                data.setAge(etAge.getText().toString().trim());
                contentValues.put(Util.AGE, data.getAge());
                contentValues.put(Util.NAME, data.getName());
                contentValues.put(Util.DESTINATION, "invalid");
                Uri uri = mContentResolver.insert(Util.USER_URI, contentValues);
                assert uri != null;
                Toast.makeText(this, "the data inserted successfully with id:" + uri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnOpenFragment:
                findViewById(R.id.flContainerMain).setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().add(R.id.flContainerMain, new IpcFragment()).addToBackStack(null).commit();
                break;
            case R.id.btnIPCRemoteBinding:
                findViewById(R.id.flContainerMain).setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().add(R.id.flContainerMain, new RemoteBindingClientFragment()).addToBackStack(null).commit();
                break;


        }
    }

    @Override public void onBackPressed() {
        if (0 < getSupportFragmentManager().getBackStackEntryCount()) {
            getSupportFragmentManager().popBackStackImmediate();
            findViewById(R.id.flContainerMain).setVisibility(View.GONE);
        } else super.onBackPressed();
    }


    @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Util.USER_URI, mProjection, null, null, null);
    }

    @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (null != data && 0 < data.getCount()) {
            List<Data> list = new ArrayList<>();
            while (data.moveToNext()) {
                String age = data.getString(0);
                String name = data.getString(1);
                String id = data.getString(2);
                Data dataModel = new Data();
                dataModel.setAge(age);
                dataModel.setName(name);
                dataModel.setId(id);
                list.add(dataModel);
            }
            mDataAdapter.updateList(list);
        }
    }

    @Override public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
