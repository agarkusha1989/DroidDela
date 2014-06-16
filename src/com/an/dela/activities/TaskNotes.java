package com.an.dela.activities;

import com.an.dela.R;
import com.an.dela.db.Db;
import com.an.dela.db.TaskContract;
import com.an.dela.db.TaskRecord;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TaskNotes extends Activity {

	public static final String EXTRA_TASK_ID = "package com.an.dela.activities.EXTRA_TASK_ID";

	private TextView titleTextView;
	private EditText notesEditText;
	private LinearLayout rootView;
	
	private TaskRecord taskRecord;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_notes);
		
		titleTextView = (TextView) findViewById(R.id.title);
		notesEditText = (EditText) findViewById(R.id.notes);
		rootView = (LinearLayout) findViewById(R.id.task_notes);
		
		if (!loadTask()) {
			Toast.makeText(this, getResources().getString(R.string.error_load_task), Toast.LENGTH_SHORT).show();
			finish();
		} else {
			
		}
	}
	
	public void onClickSaveButton(View view) {
		String notes = notesEditText.getText().toString();
		
		taskRecord.set(TaskContract.COLUMN_NOTES, notes);
		
		taskRecord = Db.getInstance(this).getTaskTable().update(taskRecord);
	}
	
	private boolean loadTask() {
		int id = getIntent().getIntExtra(EXTRA_TASK_ID, -1);
		
		if (-1 == id) return false;
		if (null == (taskRecord = Db.getInstance(this).getTaskTable().select(id))) 
			return false;
		
		titleTextView.setText(taskRecord.getTitle());
		notesEditText.setText(taskRecord.getNotes());
		notesEditText.setSelection(notesEditText.getText().length());
		int colorResId;
		
		if (taskRecord.equalPriority(TaskRecord.PRIORITY_HIGH)) {
			colorResId = getResources().getColor(R.color.priority_high);
		} else if (taskRecord.equalPriority(TaskRecord.PRIORITY_MEDIUM)) {
			colorResId = getResources().getColor(R.color.priority_medium);
		} else {
			colorResId = getResources().getColor(R.color.priority_low);
		}
		rootView.setBackgroundColor(colorResId);
		
		return true;
	}

}
