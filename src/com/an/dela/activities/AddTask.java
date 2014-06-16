package com.an.dela.activities;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

import com.an.dela.R;
import com.an.dela.db.Db;
import com.an.dela.db.TaskRecord;

public class AddTask extends Activity {

	public static final int MINUTE_INTERVAL_MIN = 1;
	public static final int MINUTE_INTERVAL_MAX = 20;
	
	private TextView titleTextView;
	private CheckBox alarmCheckBox;
	private CheckBox repeatingCheckBox;
	private Button beginDate;
	private Button beginTime;
	private Button expiresDate;
	private Button expiresTime;
	private Button priorityButton;

	private Calendar beginCalendar = Calendar.getInstance();
	private Calendar expiresCalendar = Calendar.getInstance();
	
	private int priority = TaskRecord.PRIORITY_HIGH;
	private int repeatingInterval = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);

		titleTextView = (TextView) findViewById(R.id.title);
		alarmCheckBox = (CheckBox) findViewById(R.id.is_alarm);
		repeatingCheckBox = (CheckBox) findViewById(R.id.is_repeating);
		beginDate = (Button) findViewById(R.id.begin_date);
		beginTime = (Button) findViewById(R.id.begin_time);
		expiresDate = (Button) findViewById(R.id.expires_date);
		expiresTime = (Button) findViewById(R.id.expires_time);
		priorityButton = (Button) findViewById(R.id.priority);

		beginCalendar.setTimeInMillis(System.currentTimeMillis());
		expiresCalendar.setTimeInMillis(System.currentTimeMillis());

		priorityButton.setText(getResources().getStringArray(R.array.priority)[0]);
		updateCalendars();

		alarmCheckBox.setChecked(true);
		alarmCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!isChecked) {
					repeatingCheckBox.setEnabled(false);
					beginDate.setEnabled(false);
					beginTime.setEnabled(false);
					expiresDate.setEnabled(false);
					expiresTime.setEnabled(false);
				} else {
					repeatingCheckBox.setEnabled(true);
					beginDate.setEnabled(true);
					beginTime.setEnabled(true);
					expiresDate.setEnabled(true);
					expiresTime.setEnabled(true);
				}
			}
		});
		
		repeatingCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					showSeekBarDialog();
				}
			}
		});
	}
	
	private void showSeekBarDialog() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_set_repeating_interval);
		dialog.setTitle(getString(R.string.set_repeating_interval));
		
		Button okButton = (Button) dialog.findViewById(R.id.ok_button);
		Button cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
		final TextView textView = (TextView) dialog.findViewById(R.id.interval_value);
		SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.interval);
		
		textView.setText(Integer.toString(repeatingInterval));
		seekBar.setMax(MINUTE_INTERVAL_MAX);
		seekBar.setProgress(repeatingInterval);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (progress < MINUTE_INTERVAL_MIN) {
					textView.setText("1");
					repeatingInterval = 1;
				} else {
					textView.setText(Integer.toString(progress));
					repeatingInterval = progress;
				}
			}
		});
		
		okButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
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
		boolean isAlarm = alarmCheckBox.isChecked();
		boolean isRepeating = isAlarm ? repeatingCheckBox.isChecked() : false;
		long repeatingMsInterval = repeatingInterval * 1000 * 60;
		
		TaskRecord record = TaskRecord.create(title, beginDatetime, expiresDatetime, priority);
		record.setAlarm(isAlarm);
		record.setRepeating(isRepeating);
		record.setRepeatingMsInterval(repeatingMsInterval);
		
		record = Db.getInstance(this).getTaskTable().insert(record);
		
		finish();
	}

	private void updateCalendars() {
		beginDate.setText(DateFormat.format("dd.MM.yyyy", beginCalendar));
		beginTime.setText(DateFormat.format("kk:mm", beginCalendar));

		expiresDate.setText(DateFormat.format("dd.MM.yyyy", expiresCalendar));
		expiresTime.setText(DateFormat.format("kk:mm", expiresCalendar));
	}
}
