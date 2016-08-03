package com.treebo.note.database.contract;

import android.net.Uri;

/**
 * Created by Appple on 03/08/16.
 */
public class NoteContract {
	public static final String TABLE_NAME = "notes";

	public static final Uri URI_NOTE = Uri.withAppendedPath(DBProviderContract.BASE_URI, TABLE_NAME);
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "tablename";
	public static final String COLUMNT_CONTENT = "conv_id";
	public static final String COLUMN_NOTE_ID = "status";
	public static final String COLUMN_LAST_UPDATED = "lastUpdated";

	public static final String SORT_ORDER = COLUMN_LAST_UPDATED+" desc";

	public static final String TableCreationQuery= "CREATE TABLE " + TABLE_NAME + " ( " +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_TITLE + " TEXT, " +
			COLUMN_NOTE_ID + " LONG, " +
			COLUMN_LAST_UPDATED + " LONG, " +
			COLUMNT_CONTENT + " TEXT "
			+ ");";

}
