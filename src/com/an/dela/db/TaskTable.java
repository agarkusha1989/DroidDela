package com.an.dela.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;

import com.an.db.Table;
import com.an.dela.activities.TaskNotes;

public class TaskTable implements Table<Integer, TaskRecord> {

	SQLiteOpenHelper sqliteOpenHelper;

	public TaskTable(SQLiteOpenHelper sqliteOpnHlpr) {
		sqliteOpenHelper = sqliteOpnHlpr;
	}

	@Override
	public TaskRecord select(Integer key) {
		TaskRecord record = null;

		String selection = TaskContract.COLUMN_ID + " = " + key;

		Cursor c = sqliteOpenHelper.getReadableDatabase().query(
				TaskContract.TABLE_NAME, null, selection, null, null, null,
				null);

		if (c.moveToFirst())
			record = cursorToRecord(c);

		c.close();

		return record;
	}

	@Override
	public TaskRecord[] select() {
		TaskRecord[] records = new TaskRecord[0];

		Cursor c = sqliteOpenHelper.getReadableDatabase().query(
				TaskContract.TABLE_NAME, null, null, null, null, null, null);

		if (c.moveToFirst())
			records = cursorToRecords(c);

		c.close();

		return records;
	}

	@Override
	public TaskRecord insert(TaskRecord record) {
		ContentValues values = recordToContentValues(record);

		long insertId = sqliteOpenHelper.getWritableDatabase().insert(
				TaskContract.TABLE_NAME, null, values);

		record.set(TaskContract.COLUMN_ID, (int) insertId);

		return record;
	}

	@Override
	public TaskRecord[] insert(TaskRecord[] records) {

		for (int i = 0; i < records.length; i++) {
			records[i] = insert(records[i]);
		}
		return records;
	}

	@Override
	public TaskRecord update(TaskRecord record) {
		int id = record.getInt(TaskContract.COLUMN_ID, -1);

		if (-1 != id) {
			ContentValues values = recordToContentValues(record);
			String whereClause = TaskContract.COLUMN_ID + " = " + id;

			sqliteOpenHelper.getWritableDatabase().update(
					TaskContract.TABLE_NAME, values, whereClause, null);
		}

		return record;
	}

	@Override
	public TaskRecord[] update(TaskRecord[] records) {

		for (int i = 0; i < records.length; i++) {
			update(records[i]);
		}

		return records;
	}

	@Override
	public void delete(TaskRecord record) {
		int id = record.getInt(TaskContract.COLUMN_ID, -1);

		if (-1 != id) {
			String whereClause = TaskContract.COLUMN_ID + " = " + id;

			sqliteOpenHelper.getWritableDatabase().delete(
					TaskContract.TABLE_NAME, whereClause, null);
		}
	}

	@Override
	public void delete(TaskRecord[] records) {
		for (int i = 0; i < records.length; i++)
			delete(records[i]);
	}

	@Override
	public void clear() {
		sqliteOpenHelper.getWritableDatabase().delete(TaskContract.TABLE_NAME,
				null, null);
	}

	private ContentValues recordToContentValues(TaskRecord record) {
		ContentValues values = new ContentValues();

		int id = record.getInt(TaskContract.COLUMN_ID, -1);
		String title = record.getString(TaskContract.COLUMN_TITLE, null);
		String notes = record.getString(TaskContract.COLUMN_NOTES, null);
		String datetime = record.getString(TaskContract.COLUMN_DATETIME, null);
		String expires = record.getString(TaskContract.COLUMN_DATETIME_EXPIRES,
				null);
		int priority = record.getInt(TaskContract.COLUMN_PRIORITY, -1);
		int status = record.getInt(TaskContract.COLUMN_STATUS, -1);

		if (-1 != id)
			values.put(TaskContract.COLUMN_ID, id);
		if (null != title)
			values.put(TaskContract.COLUMN_TITLE, title);
		if (null == datetime)
			datetime = DateFormat.format("yyyy-MM-dd kk:mm:ss",
					System.currentTimeMillis()).toString();
		values.put(TaskContract.COLUMN_DATETIME, datetime);
		if (null == expires)
			expires = DateFormat.format("yyyy-MM-dd kk:mm:ss",
					System.currentTimeMillis()).toString();
		values.put(TaskContract.COLUMN_DATETIME_EXPIRES, expires);
		if (-1 != priority)
			values.put(TaskContract.COLUMN_PRIORITY, priority);
		if (-1 != status)
			values.put(TaskContract.COLUMN_STATUS, status);
		if (null != notes) 
			values.put(TaskContract.COLUMN_NOTES, notes);

		return values;
	}

	private TaskRecord cursorToRecord(Cursor c) {
		TaskRecord record = new TaskRecord();

		record.set(TaskContract.COLUMN_ID,
				c.getInt(c.getColumnIndex(TaskContract.COLUMN_ID)));
		record.set(TaskContract.COLUMN_TITLE,
				c.getString(c.getColumnIndex(TaskContract.COLUMN_TITLE)));
		record.set(TaskContract.COLUMN_DATETIME,
				c.getString(c.getColumnIndex(TaskContract.COLUMN_DATETIME)));
		record.set(TaskContract.COLUMN_DATETIME_EXPIRES, c.getString(c
				.getColumnIndex(TaskContract.COLUMN_DATETIME_EXPIRES)));
		record.set(TaskContract.COLUMN_PRIORITY,
				c.getInt(c.getColumnIndex(TaskContract.COLUMN_PRIORITY)));
		record.set(TaskContract.COLUMN_STATUS,
				c.getInt(c.getColumnIndex(TaskContract.COLUMN_STATUS)));
		record.set(TaskContract.COLUMN_NOTES,
				c.getString(c.getColumnIndex(TaskContract.COLUMN_NOTES)));

		return record;
	}

	private TaskRecord[] cursorToRecords(Cursor c) {
		TaskRecord[] records = new TaskRecord[c.getCount()];

		do {
			records[c.getPosition()] = cursorToRecord(c);
		} while (c.moveToNext());

		return records;
	}

}
