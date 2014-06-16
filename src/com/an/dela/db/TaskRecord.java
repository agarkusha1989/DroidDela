package com.an.dela.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.an.db.Record;

public class TaskRecord extends Record {

	public static final int PRIORITY_HIGH   = 0;
	public static final int PRIORITY_MEDIUM = 1;
	public static final int PRIORITY_LOW    = 2;
	
	public static final int STATUS_NEW 		 = 0;
	public static final int STATUS_PERFORMED = 1;
	public static final int STATUS_COMPLETED = 2;
	
	private boolean isAlarm = false;
	private boolean isRepeating = false;
	private long repeatingMsInterval = 10000;
	
	public boolean equalStatus(int status) {
		Integer thisStatus = getInt(TaskContract.COLUMN_STATUS, -1);
		return thisStatus.equals(status); 
	}
	
	public boolean equalPriority(int priority) {
		Integer thisPriority = getInt(TaskContract.COLUMN_PRIORITY, -1);
		return thisPriority.equals(priority);
	}
	
	public int getId() {
		return getInt(TaskContract.COLUMN_ID, 0);
	}
	
	public String getTitle() {
		return getString(TaskContract.COLUMN_TITLE, "");
	}
	
	public String getNotes() {
		return getString(TaskContract.COLUMN_NOTES, "");
	}
	
	public int getPriority() {
		return getInt(TaskContract.COLUMN_PRIORITY, -1);
	}
	
	public int getStatus() {
		return getInt(TaskContract.COLUMN_STATUS, -1);
	}
	
	public Date getDateDatetime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formatter.parse(getStringDatetime());
		} catch (ParseException e) {
			return null;
		}
	}
	
	public Date getDateDatetimeExpires() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formatter.parse(getStringDatetimeExpires());
		} catch (ParseException e) {
			return null;
		}
	}
	
	public String getStringDatetime() {
		return getString(TaskContract.COLUMN_DATETIME, "0000-00-00 00:00:00");
	}
	
	public String getStringDatetimeExpires() {
		return getString(TaskContract.COLUMN_DATETIME_EXPIRES, "0000-00-00 00:00:00");
	}
	
	public boolean isAlarm() {
		return isAlarm;
	}

	public void setAlarm(boolean isAlarm) {
		this.isAlarm = isAlarm;
	}
	
	public void setAlarm(int isAlarm) {
		this.isAlarm = (isAlarm != 0);
	}

	public boolean isRepeating() {
		return isRepeating;
	}

	public void setRepeating(boolean isRepeating) {
		this.isRepeating = isRepeating;
	}
	
	public void setRepeating(int isRepeating) {
		this.isRepeating = (isRepeating != 0);
	}

	public long getRepeatingMsInterval() {
		return repeatingMsInterval;
	}

	public void setRepeatingMsInterval(long repeatingMsInterval) {
		this.repeatingMsInterval = repeatingMsInterval;
	}

	public static TaskRecord create(String title, String datetime, String expires, int priority) {
		TaskRecord r = new TaskRecord();
		r.set(TaskContract.COLUMN_TITLE, title);
		r.set(TaskContract.COLUMN_DATETIME, datetime);
		r.set(TaskContract.COLUMN_DATETIME_EXPIRES, expires);
		r.set(TaskContract.COLUMN_PRIORITY, priority);
		r.set(TaskContract.COLUMN_STATUS, STATUS_NEW);
		return r;
	}
	
	public static TaskRecord createHigh(String title, String datetime, String expires) {
		return create(title, datetime, expires, PRIORITY_HIGH);
	}
	
	public static TaskRecord createMedium(String title, String datetime, String expires) {
		return create(title, datetime, expires, PRIORITY_MEDIUM);
	}
	
	public static TaskRecord createLow(String title, String datetime, String expires) {
		return create(title, datetime, expires, PRIORITY_LOW);
	}
}
