package com.treebo.note.activities;

import com.treebo.note.R;
import com.treebo.note.database.contract.NoteContract;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by Appple on 03/08/16.
 */
public class TakeANoteActivity extends AppCompatActivity {
	EditText mNoteTitle, mNoteContent;

	public static Intent getIntent(Context context) {
		return new Intent(context, TakeANoteActivity.class);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_a_note_layout);
		initViews();
	}


	private void initViews() {
		mNoteContent = (EditText) findViewById(R.id.take_note_content);
		mNoteTitle = (EditText) findViewById(R.id.take_note_title);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.take_a_note_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.save_note) {
			String title = mNoteTitle.getText().toString().trim();
			String content = mNoteContent.getText().toString().trim();
			long noteId = System.currentTimeMillis();
			long lastUPdated = System.currentTimeMillis();
			ContentValues values = NoteContract.getInsertValues(title, content, noteId, lastUPdated);
			getContentResolver().insert(NoteContract.URI_NOTE, values);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}

