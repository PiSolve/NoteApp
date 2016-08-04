package com.treebo.note.adapters;

import com.treebo.note.R;
import com.treebo.note.activities.EditANoteActivity;
import com.treebo.note.database.contract.NoteContract;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Appple on 03/08/16.
 */
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.BaseVH> {

	Cursor mCursor;
	Context mContext;

	public static NoteListAdapter getInstance(Context context, Cursor cursor) {
		return new NoteListAdapter(context, cursor);
	}

	public NoteListAdapter(Context context, Cursor cursor) {
		mContext = context;
		mCursor = cursor;

	}

	@Override
	public BaseVH onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(mContext).inflate(R.layout.single_note_layout, null, false);
		return new NoteVh(v);
	}

	@Override
	public void onBindViewHolder(BaseVH holder, int position) {
		NoteVh vh = (NoteVh) holder;
		mCursor.moveToPosition(position);
		String title = mCursor.getString(mCursor.getColumnIndex(NoteContract.COLUMN_TITLE));
		String content = mCursor.getString(mCursor.getColumnIndex(NoteContract.COLUMNT_CONTENT));
		long notId = mCursor.getLong(mCursor.getColumnIndex(NoteContract.COLUMN_NOTE_ID));
		vh.id = notId;
		vh.noteContent = content;
		vh.noteTitle = title;
		vh.title.setText(title);
		vh.content.setText(content);

	}

	@Override
	public int getItemCount() {
		return mCursor != null ? mCursor.getCount() : 0;
	}

	public void swapCursor(Cursor data) {
		mCursor = data;
		notifyDataSetChanged();
	}


	public class NoteVh extends BaseVH implements View.OnClickListener {
		TextView title, content;
		ImageView editNote, deleteNote;
		long id;
		String noteTitle, noteContent;

		public NoteVh(View itemView) {
			super(itemView);
			title = (TextView) itemView.findViewById(R.id.note_title);
			content = (TextView) itemView.findViewById(R.id.content);
			editNote = (ImageView) itemView.findViewById(R.id.edit_note);
			deleteNote = (ImageView) itemView.findViewById(R.id.delete_note);
			editNote.setOnClickListener(this);
			deleteNote.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.edit_note) {
				Intent i = EditANoteActivity.getIntent(mContext, noteTitle, noteContent, id);
				mContext.startActivity(i);
			}
			if (v.getId() == R.id.delete_note) {
				mContext.getContentResolver().delete(NoteContract.URI_NOTE, NoteContract.COLUMN_NOTE_ID + " like '" + id + "'", null);
				Toast.makeText(mContext, "Note deleted", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public class BaseVH extends RecyclerView.ViewHolder {
		public BaseVH(View itemView) {
			super(itemView);
		}
	}
}
