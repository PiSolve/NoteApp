package com.treebo.note.database;

import com.treebo.note.database.contract.NoteContract;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Appple on 03/08/16.
 */
public class DataProvider extends ContentProvider {
	public static final String AUTHORITY = "com.treebo.note.provider.auth";
	public static final String BASE_URI_STRING = "content://" + AUTHORITY;
	private static final UriMatcher URI_MATCHER;
	private DatabaseHelper helper;
	private static final int NOTE_IDENTIFIER = 01;

	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

		URI_MATCHER.addURI(AUTHORITY, NoteContract.TABLE_NAME, NOTE_IDENTIFIER);
	}

	@Override
	public boolean onCreate() {
		helper = DatabaseHelper.getInstance(getContext());
		return helper != null;
	}

	@Nullable
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		cursor = helper.query(NoteContract.TABLE_NAME, projection, selection, selectionArgs, sortOrder);

		return cursor;
	}

	@Nullable
	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Nullable
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri uri1 = null;
		long id = helper.insertRow(NoteContract.TABLE_NAME, values);
		uri1 = Uri.withAppendedPath(uri, " " + id);
		getContext().getContentResolver().notifyChange(uri, null);
		return uri1;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		helper.delete(NoteContract.TABLE_NAME, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count = 0;
		helper.update(NoteContract.TABLE_NAME, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}
