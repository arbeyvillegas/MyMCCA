package vandy.mooc.model.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class VideoUpClientProvider extends ContentProvider {

    private DatabaseHelper mOpenHelper;

    private static final UriMatcher sUriMatcher =
            buildUriMatcher();
    
    private static final int ACRONYMS = 100;

    private static final int ACRONYM = 101;
    
    /**
     * Hook method called when Database is created to initialize the
     * Database Helper that provides access to the Acronym Database.
     */
    @Override
    public boolean onCreate() {
        mOpenHelper =
                new DatabaseHelper(getContext());
        return true;
    }

    private static UriMatcher buildUriMatcher() {
        // All paths added to the UriMatcher have a corresponding code
        // to return when a match is found.  The code passed into the
        // constructor represents the code to return for the rootURI.
        // It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher =
                new UriMatcher(UriMatcher.NO_MATCH);

        // For each type of URI that is added, a corresponding code is
        // created.
        matcher.addURI(VideoContract.CONTENT_AUTHORITY,
                VideoContract.PATH_ACRONYM,
                ACRONYMS);
        matcher.addURI(VideoContract.CONTENT_AUTHORITY,
                VideoContract.PATH_ACRONYM
                        + "/#",
                ACRONYM);
        return matcher;
    }
    
//    /**
//     * Hook method called to handle requests for the MIME type of the
//     * data at the given URI.  The returned MIME type should start
//     * with vnd.android.cursor.item for a single item or
//     * vnd.android.cursor.dir/ for multiple items.
//     */
//    @Override
    public String getType(Uri uri) {
    	final int match = sUriMatcher.match(uri);
    	
    	switch (match) {
        case ACRONYMS:
            return VideoContract.AcronymEntry.CONTENT_ITEMS_TYPE;
        case ACRONYM:
            return VideoContract.AcronymEntry.CONTENT_ITEM_TYPE;
        default:
            throw new UnsupportedOperationException("Unknown uri: "
                    + uri);
    }
    }

    /**
     * Hook method called to handle requests to insert a new row.  As
     * a courtesy, notifyChange() is called after inserting.
     */
    @Override
    public Uri insert(Uri uri,
                      ContentValues values) {
        final SQLiteDatabase db =
                mOpenHelper.getWritableDatabase();

        
                db.insert(VideoContract.AcronymEntry.TABLE_NAME, null, values);


        // Notifies registered observers that a row was inserted.
        getContext().getContentResolver().notifyChange(uri,
                null);
        
        return null;
    }

    // Hook method to handle requests to insert a set of new rows, or
    // the default implementation will iterate over the values and
    // call insert on each of them. As a courtesy, call notifyChange()
    // after inserting.
    @Override
    public int bulkInsert(Uri uri,
                          ContentValues[] contentValues) {
        final SQLiteDatabase db =
                mOpenHelper.getWritableDatabase();

                int returnCount = 0;
//
                    for (ContentValues currentValues : contentValues) {
                        long id = db.insert(VideoContract.AcronymEntry.TABLE_NAME, null, currentValues);
                        if (id != -1) {
                            returnCount++;
                        }
                    }
//
                getContext().getContentResolver().notifyChange(uri,
                        null);
                return returnCount;
    }

    /**
     * Hook method called to handle query requests from clients.
     */
    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();


                retCursor = db.query(VideoContract.AcronymEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        retCursor.setNotificationUri(getContext().getContentResolver(),
                uri);
        return retCursor;
    }

    /**
     * Hook method called to handle requests to update one or more
     * rows. The implementation should update all rows matching the
     * selection to set the columns according to the provided values
     * map. As a courtesy, notifyChange() is called after updating .
     */
    @Override
    public int update(Uri uri,
                      ContentValues values,
                      String selection,
                      String[] selectionArgs) {
        // Create and/or open a database that will be used for reading
        // and writing. Once opened successfully, the database is
        // cached, so you can call this method every time you need to
        // write to the database.
        final SQLiteDatabase db =
                mOpenHelper.getWritableDatabase();

        int rowsUpdated;

        
                rowsUpdated = db.update(VideoContract.AcronymEntry.TABLE_NAME, values, selection, selectionArgs);

        // Notifies registered observers that rows were updated.
        if (rowsUpdated != 0)
            getContext().getContentResolver().notifyChange(uri,
                    null);
        return rowsUpdated;
    }

    /**
     * Hook method to handle requests to delete one or more rows.  The
     * implementation should apply the selection clause when
     * performing deletion, allowing the operation to affect multiple
     * rows in a directory.  As a courtesy, notifyChange() is called
     * after deleting.
     */
    @Override
    public int delete(Uri uri,
                      String selection,
                      String[] selectionArgs) {
        // Create and/or open a database that will be used for reading
        // and writing. Once opened successfully, the database is
        // cached, so you can call this method every time you need to
        // write to the database.
        final SQLiteDatabase db =
                mOpenHelper.getWritableDatabase();

        // Keeps track of the number of rows deleted.
        int rowsDeleted = 0;

                rowsDeleted = db.delete(VideoContract.AcronymEntry.TABLE_NAME, selection, selectionArgs);


        // Notifies registered observers that rows were deleted.
        if (selection == null || rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri,
                    null);
        return rowsDeleted;
    }
}
