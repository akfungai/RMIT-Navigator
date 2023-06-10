package com.example.project;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "navigationDatabase";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStepsTable = "CREATE TABLE STEPS (ID INTEGER PRIMARY KEY AUTOINCREMENT, LOCATION TEXT, STEP TEXT);";
        db.execSQL(createStepsTable);

        // Insert Steps into the table
        String insertSteps = "INSERT INTO STEPS (LOCATION, STEP) VALUES" +
                "('Library', 'Navigate to building 22 on the corner of Latrobe and Swanston street')," +
                "('Library', 'Move down Swanston street until you reach the entrance of building 10')," +
                "('Library', 'Enter building 10 through the turning doors (placeholder image)')," +
                "('Library', 'Ride the escalator to floor 3')," +
                "('Library', 'Ride the next escalator up to floor 4')," +
                "('Library', 'The library is forward and right')";
        db.execSQL(insertSteps);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STEPS");
        onCreate(db);
    }
}
