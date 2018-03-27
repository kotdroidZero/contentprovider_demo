package com.kotdroid.contentproviderdemo;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etId) EditText etId;


    private ContactAdapter mContactAdapter;

    private boolean isFirstTimeLoading = true;


    String[] mSelectionClause;
    String[] mProjection = new String[]{ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID};

    Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private List<Contact> contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ButterKnife.bind(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mContactAdapter = new ContactAdapter(this);
        recyclerView.setAdapter(mContactAdapter);
    }

    @OnClick({R.id.btnLoadContact, R.id.btnSaveContact, R.id.btnDeleteContact, R.id.btnUpdateContact})
    public void clickMe(View view) {
        switch (view.getId()) {
            case R.id.btnLoadContact:
                if (isFirstTimeLoading) {
                    getLoaderManager().initLoader(1, null, this);
                    isFirstTimeLoading = false;
                } else {
                    getLoaderManager().restartLoader(1, null, this);
                }
                break;
            case R.id.btnSaveContact:
                saveToContacts();
                break;
            case R.id.btnDeleteContact:
                deleteFromContact();
                break;
            case R.id.btnUpdateContact:
                updateContact();
                break;

        }
    }

    private void updateContact() {
        String where = ContactsContract.RawContacts._ID + " = ? ";
        String[] params = new String[]{etId.getText().toString().trim()};

        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY, etName.getText().toString().trim());
        getContentResolver().update(ContactsContract.RawContacts.CONTENT_URI, contentValues, where, params);
    }

    private void deleteFromContact() {
        String whereClause = ContactsContract.RawContacts._ID + " = '" + etId.getText().toString().trim() + "'";
        getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, whereClause, null);
        findViewById(R.id.btnLoadContact).performClick();
    }

    private void saveToContacts() {

    }


    @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, contactUri, mProjection, null, null, null);
    }

    @Override public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (null != cursor && 0 < cursor.getCount()) {
            contacts = new ArrayList<>();
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                String number = cursor.getString(1);
                String id = cursor.getString(2);
                contacts.add(new Contact(name, number, id));
            }
            mContactAdapter.updateList(contacts);
        }
    }

    @Override public void onLoaderReset(Loader<Cursor> loader) {

    }
}
