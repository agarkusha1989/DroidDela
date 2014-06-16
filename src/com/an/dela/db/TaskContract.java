package com.an.dela.db;

public class TaskContract {

	public static final String TABLE_NAME = "task_table";

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TITLE = "task_title";
	public static final String COLUMN_DATETIME = "task_datetime";
	public static final String COLUMN_DATETIME_EXPIRES = "task_datetime_expires";
	public static final String COLUMN_PRIORITY = "task_priority";
	public static final String COLUMN_STATUS = "task_status";
	public static final String COLUMN_NOTES = "task_notes";

	public static final String SQL_CREATE_TABLE = "create table " + TABLE_NAME + " ("
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_TITLE + " text not null, " 
			+ COLUMN_NOTES + " text default '', "
			+ COLUMN_DATETIME + " datetime not null, "
			+ COLUMN_DATETIME_EXPIRES + " datetime not null, "
			+ COLUMN_PRIORITY + " int not null default " + TaskRecord.PRIORITY_LOW + ", " 
			+ COLUMN_STATUS + " int not null default " + TaskRecord.STATUS_NEW + ")";

	public static final String SQL_DROP_TABLE = "drop table if exists "
			+ TABLE_NAME;

}
