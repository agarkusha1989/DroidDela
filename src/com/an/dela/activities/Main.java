package com.an.dela.activities;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import com.an.dela.R;
import com.an.dela.activities.adapters.TaskListAdapter;
import com.an.dela.db.Db;
import com.an.dela.db.TaskContract;
import com.an.dela.db.TaskRecord;

public class Main extends ListActivity {

	private int selectedStatus = TaskRecord.STATUS_NEW; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onStart() {
		super.onStart();
		updateTaskListView();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		TaskRecord item = (TaskRecord) getListAdapter().getItem(position);

		Intent intent = new Intent(this, TaskNotes.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(TaskNotes.EXTRA_TASK_ID, item.getId());
		startActivity(intent);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.task_actions, menu);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		TaskRecord taskRecord = (TaskRecord)getListAdapter().getItem(info.position);
		
		switch (item.getItemId()) {
		case R.id.action_notes:
			actionTaskNotes(taskRecord);
			return true;
		case R.id.action_change:
			actionChangeTask(taskRecord);
			return true;
		case R.id.action_delete:
			Db.getInstance(this).getTaskTable().delete(taskRecord);
			updateTaskListView();
			return true;
		case R.id.action_set_status:
			actionSetStatus(taskRecord);
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_add_task:
			Intent intent = new Intent(this, AddTask.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.startActivity(intent);
			return true;
		case R.id.action_help:
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void actionTaskNotes(TaskRecord taskRecord) {
		Intent intent = new Intent(this, TaskNotes.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(TaskNotes.EXTRA_TASK_ID, taskRecord.getId());
		startActivity(intent);
	}
	
	private void actionChangeTask(TaskRecord taskRecord) {
		Intent intent = new Intent(this, ChangeTask.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(ChangeTask.EXTRA_TASK_ID, taskRecord.getId());
		startActivity(intent);
	}
	
	private void actionSetStatus(final TaskRecord taskRecord) {
		final String[] statusArray = getResources().getStringArray(R.array.status);
	
		selectedStatus = taskRecord.getStatus();
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(getResources().getString(R.id.action_set_status));
		dialog.setSingleChoiceItems(statusArray, taskRecord.getStatus(), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				onChangeTaskStatus(taskRecord, which);
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	private void onChangeTaskStatus(TaskRecord taskRecord, int status) {
		taskRecord.set(TaskContract.COLUMN_STATUS, status);
		taskRecord = Db.getInstance(this).getTaskTable().update(taskRecord);
		updateTaskListView();
	}
	
	private void updateTaskListView() {
		TaskRecord[] records = Db.getInstance(this).getTaskTable().select();

		setListAdapter(new TaskListAdapter(this, records));

		registerForContextMenu(getListView());
	}

}