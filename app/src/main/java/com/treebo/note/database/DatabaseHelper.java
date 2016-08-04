package com.treebo.note.database;

import com.treebo.note.database.contract.NoteContract;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Appple on 03/08/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "treebo.note";
	private static DatabaseHelper instance;
	private SQLiteDatabase db;
	private Context mContext;
	private static final String TAG = DatabaseHelper.class.getName();

	public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	public static DatabaseHelper getInstance(final Context context) {

		synchronized (DatabaseHelper.class)

		{
			if (instance == null) {
				instance = new DatabaseHelper(context);
			}
		}

		return instance;
	}

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, new DBErrorHandler());
		mContext = context;
		db = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(NoteContract.TableCreationQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	private static class DBErrorHandler implements DatabaseErrorHandler {


		@Override
		public void onCorruption(SQLiteDatabase dbObj) {
			Log.e(TAG, "some error in db-------------------------------------");
		}
	}

	public Cursor query(String tableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Cursor cursor = getReadableDatabase().query(tableName, projection, selection, selectionArgs, null, null, sortOrder);

		return cursor;
	}

	public int delete(String tableName, String selection, String[] selectionArgs) {
		int id = getWritableDatabase().delete(tableName, selection, selectionArgs);
		Log.d(TAG, " deleted entry  tableName " + tableName + " selection is " + selection);
		return id;
	}

	public int update(String tableName, ContentValues values, String selection, String[] selectionArgs) {
		if (db != null && !db.isOpen()) {
			db = getWritableDatabase();
		}
		int id = db.update(tableName, values, selection, selectionArgs);

		return id;


	}

	public long insertRow(String tableName, ContentValues values) {
		if (db == null || !db.isOpen()) {
			db = getWritableDatabase();
		}
		return db.insert(tableName, null, values);
	}



}
