package com.treebo.note.activities;

import com.treebo.note.R;
import com.treebo.note.database.contract.NoteContract;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by Appple on 03/08/16.
 */
public class EditANoteActivity extends AppCompatActivity {
	private static final String TITLE = "title";
	private static final String CONTENT = "content";
	private static final String NOTE_ID = "noteId";
	private EditText mNoteContent;
	private EditText mNoteTitle;
	String mExtraTitle, mExtraContent;
	long mExtraNoteID;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_a_note_layout);
		getDataFromBUndle();
		initViews();
	}

	private void initViews() {
		mNoteContent = (EditText) findViewById(R.id.take_note_content);
		mNoteTitle = (EditText) findViewById(R.id.take_note_title);
		mNoteTitle.setText(mExtraTitle);
		mNoteContent.setText(mExtraContent);
	}

	private void getDataFromBUndle() {
		if (getIntent() != null && getIntent().getExtras() != null) {
			mExtraTitle = getIntent().getStringExtra(TITLE);
			mExtraContent = getIntent().getStringExtra(CONTENT);
			mExtraNoteID = getIntent().getLongExtra(NOTE_ID, 0);
		}
	}

	public static Intent getIntent(Context context, String title, String content, long id) {
		Bundle b = new Bundle();
		b.putString(TITLE, title);
		b.putString(CONTENT, content);
		b.putLong(NOTE_ID, id);
		Intent i = new Intent(context, EditANoteActivity.class);
		i.putExtras(b);
		return i;
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
		getMenuInflater().inflate(R.menu.take_a_note_menu,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.save_note){
			saveChangesAndExit();
		}
		if(item.getItemId()==android.R.id.home){
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		askConfirmationBeforeExit();

	}

	private void askConfirmationBeforeExit() {
		new AlertDialog.Builder(this)
				.setTitle("")
				.setMessage("Do you want to save the changes before you exit")
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						saveChangesAndExit();
					}
				})
				.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).create().show();
	}


	private void saveChangesAndExit() {
		String title = mNoteTitle.getText().toString().trim();
		String content = mNoteContent.getText().toString().trim();
		long lastUPdated = System.currentTimeMillis();
		ContentValues values = NoteContract.getInsertValues(title, content, mExtraNoteID, lastUPdated);
		getContentResolver().update(NoteContract.URI_NOTE, values,NoteContract.COLUMN_NOTE_ID+" like '"+mExtraNoteID+"'",null);
		finish();
	}
}
