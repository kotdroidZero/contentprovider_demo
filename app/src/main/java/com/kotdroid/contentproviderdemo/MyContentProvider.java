package com.kotdroid.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

public class MyContentProvider extends ContentProvider {


    private DbHelper mDbHelper;
    private SQLiteDatabase mSqLiteDatabase;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        String tabelName = uri.getLastPathSegment();
        String intentCp = values.getAsString(Util.DESTINATION);
        if (!intentCp.equalsIgnoreCase("invalid")) {
            Class mainActivityClass = null;
            try {
                mainActivityClass = Class.forName(intentCp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assert mainActivityClass != null;
            getContext().startActivity(new Intent(getContext(), mainActivityClass));
        }
        long id = mSqLiteDatabase.insert(tabelName, null, values);
        return Uri.parse("dummyUri/" + id);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        mSqLiteDatabase = mDbHelper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {


        return mSqLiteDatabase.query(uri.getLastPathSegment(), projection, selection, selectionArgs, sortOrder, null, null);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class DbHelper extends SQLiteOpenHelper {

        DbHelper(Context context) {
            super(context, Util.DATABASE_NAME, null, 1);
        }

        @Override public void onCreate(SQLiteDatabase db) {
            db.execSQL(Util.CREATE_TABLE);
        }

        @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
