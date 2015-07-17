package vandy.mooc.model.provider;

import java.io.File;

import vandy.mooc.model.provider.VideoContract.AcronymEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * If the database schema is changed, the database version must be
     * incremented.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Database name.
     */
    public static final String DATABASE_NAME =
        "VIDEO.db";

    /**
     * Constructor for AcronymDatabaseHelper.  Store the database in
     * the cache directory so Android can remove it if memory is low.
     * 
     * @param context
     */
    public DatabaseHelper(Context context) {
    	super(context, 
              context.getCacheDir()
              + File.separator 
              + DATABASE_NAME,
              null, 
              DATABASE_VERSION);
    }

    /**
     * Hook method called when Database is created.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Define an SQL string that creates a table to hold Acronyms.
        // Each Acronym has a list of LongForms in Json.
        final String SQL_CREATE_ACRONYM_TABLE =
            "CREATE TABLE "
            + AcronymEntry.TABLE_NAME + " (" 
            + AcronymEntry._ID + " bigint AUTO_INCREMENT PRIMARY KEY, "
            + AcronymEntry.COLUMN_VIDEO_ID + " varchar(255) NULL, "
            + AcronymEntry.COLUMN_TYPE + " varchar(255) NOT NULL, "
            + AcronymEntry.COLUMN_LOCATION + " varchar(255) NOT NULL, "
            + AcronymEntry.COLUMN_RAITING + " tinyint NOT NULL, " 
            + AcronymEntry.COLUMN_TITLE + " varchar(255) NOT NULL "  
            + " );";
        
        // Create the table.
        db.execSQL(SQL_CREATE_ACRONYM_TABLE);
    }

    /**
     * Hook method called when Database is upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {
        // This database is only a cache for online data, so its
        // upgrade policy is to simply to discard the data and start
        // over.  This method only fires if you change the version
        // number for your database.  It does NOT depend on the
        // version number for your application.  If the schema is
        // updated without wiping data, commenting out the next 2
        // lines should be the top priority before modifying this
        // method.
        db.execSQL("DROP TABLE IF EXISTS " 
                   + AcronymEntry.TABLE_NAME);
        onCreate(db);
    }
}
