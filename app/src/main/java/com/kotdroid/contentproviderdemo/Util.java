package com.kotdroid.contentproviderdemo;

import android.net.Uri;
import android.os.Environment;

/**
 * Created by user12 on 27/3/18.
 */

public class Util {

    public static final String DATABASE_NAME = "demoCPDb";
    public static final String TABLE_NAME = "demoCPTable";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String ID = "id";
    public static final String DESTINATION = "destination";

    public static final String TEXT_FILE_PREFIX = "TEXT_";
    public static final String TEXT_FILE_SUFFIX = ".txt";

    public static final String LOCAL_STORAGE_BASE_PATH_FOR_FILES = Environment
            .getExternalStorageDirectory() + "/ContentProvider";


    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            NAME + " TEXT," +
            AGE + " TEXT," +
            DESTINATION + " TEXT," +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT )";

    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME + " IF EXIST";

    public static final Uri USER_URI = Uri.parse("content://com.kotdroid.contentproviderdemo.usercontentprovider/" + TABLE_NAME);

}
