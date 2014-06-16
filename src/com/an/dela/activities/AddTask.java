package com.an.dela.activities;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.an.dela.R;
import com.an.dela.db.Db;
import com.an.dela.db.TaskRecord;

public class AddTask extends Activity {

	private TextView titleTextView;
	private Button beginDate;
	private Button beginTime;
	private Button expiresDate;
	private Button expiresTime;
	private Button priorityButton;

	private Calendar beginCalendar = Calendar.getInstance();
	private Calendar expiresCalendar = Calendar.getInstance();
	
	private int priority = TaskRecord.PRIORITY_HIGH;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);

		titleTextView = (TextView) findViewById(R.id.title);
		beginDate = (Button) findViewById(R.id.begin_date);
		beginTime = (Button) findViewById(R.id.begin_time);
		expiresDate = (Button) findViewById(R.id.expires_date);
		expiresTime = (Button) findViewById(R.id.expires_time);
		priorityButton = (Button) findViewById(R.id.priority);

		beginCalendar.setTimeInMillis(System.currentTimeMillis());
		expiresCalendar.setTimeInMillis(System.currentTimeMillis());

		priorityButton.setText(getResources().getStringArray(R.array.priority)[0]);
		updateCalendars();
	}

	public void onClickBeginDate(View view) {
		DatePickerDialog dialog = new DatePickerDialog(this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						beginCalendar.set(Calendar.YEAR, year);
						beginCalendar.set(Calendar.MONTH, monthOfYear);
						beginCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
						updateCalendars();
					}
				}, beginCalendar.get(Calendar.YEAR),
				beginCalendar.get(Calendar.MONTH),
				beginCalendar.get(Calendar.DAY_OF_MONTH));

		dialog.show();
	}

	public void onClickBeginTime(View view) {
		TimePickerDialog dialog = new TimePickerDialog(this,
				new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						beginCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						beginCalendar.set(Calendar.MINUTE, minute);
						updateCalendars();
					}
				}, beginCalendar.get(Calendar.HOUR_OF_DAY),
				beginCalendar.get(Calendar.MINUTE), true);
		dialog.show();
	}

	public void onClickExpiresDate(View view) {
		DatePickerDialog dialog = new DatePickerDialog(this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						expiresCalendar.set(Calendar.YEAR, year);
						expiresCalendar.set(Calendar.MONTH, monthOfYear);
						expiresCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
						updateCalendars();
					}
				}, expiresCalendar.get(Calendar.YEAR),
				expiresCalendar.get(Calendar.MONTH),
				expiresCalendar.get(Calendar.DAY_OF_MONTH));

		dialog.show();
	}

	public void onClickExpiresTime(View view) {
		TimePickerDialog dialog = new TimePickerDialog(this,
				new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						expiresCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						expiresCalendar.set(Calendar.MINUTE, minute);
						updateCalendars();
					}
				}, expiresCalendar.get(Calendar.HOUR_OF_DAY),
				expiresCalendar.get(Calendar.MINUTE), true);
		dialog.show();
	}

	public void onClickPriority(View view) {
		final String[] priorityArray = getResources().getStringArray(R.array.priority);
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(getResources().getString(R.string.priority));
		dialog.setSingleChoiceItems(priorityArray, 0, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				priority = which;
				priorityButton.setText(priorityArray[which]);
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	public void onClickSaveButton(View view) {
		String title = titleTextView.getText().toString();
		String beginDatetime = DateFormat.format("yyyy-MM-dd kk:mm:ss", beginCalendar).toString();
		String expiresDatetime = DateFormat.format("yyyy-MM-dd kk:mm:ss", expiresCalendar).toString();
		
		Db.getInstance(this).getTaskTable().insert(TaskRecord.create(title, beginDatetime, expiresDatetime, priority));
		
		finish();
	}

	private void updateCalendars() {
		beginDate.setText(DateFormat.format("dd.MM.yyyy", beginCalendar));
		beginTime.setText(DateFormat.format("kk:mm", beginCalendar));

		expiresDate.setText(DateFormat.format("dd.MM.yyyy", expiresCalendar));
		expiresTime.setText(DateFormat.format("kk:mm", expiresCalendar));
	}
}
