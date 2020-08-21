package com.mukhi.bobaorbust;

import android.content.Context;

/**
 *  This class uses the Singleton design pattern to ensure there is only one instance of the
 *  database is created.
 */
public class DatabaseSingleton {

    private static DatabaseSingleton singleInstance = null;

    public DatabaseHelper databaseHelper;

    private DatabaseSingleton(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static DatabaseSingleton getInstance(Context context) {

        if (singleInstance == null) {
            singleInstance = new DatabaseSingleton(context);
        }

        return singleInstance;

    }

}
