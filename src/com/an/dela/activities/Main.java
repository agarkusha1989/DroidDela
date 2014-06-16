package com.an.dela.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.an.dela.R;
import com.an.dela.activities.adapters.TaskListAdapter;
import com.an.dela.db.Db;
import com.an.dela.db.TaskRecord;

public class Main extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TaskRecord[] records = Db.getInstance(this).getTaskTable().select();

		setListAdapter(new TaskListAdapter(this, records));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		Toast.makeText(this, "Выбрана задача: " + item, Toast.LENGTH_SHORT)
				.show();
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
	            actionAddTask();
	            return true;
	        case R.id.action_help:
	        	actionHelp();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void actionAddTask() {
		
	}
	
	private void actionHelp() {
		
	}

}
