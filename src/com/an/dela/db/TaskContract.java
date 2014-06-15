package com.an.dela.db;

public class TaskContract {

	public static final String TABLE_NAME = "task_table";

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TITLE = "task_title";
	public static final String COLUMN_DATETIME = "task_datetime";
	public static final String COLUMN_PRIORITY = "task_priority";
	public static final String COLUMN_STATUS = "task_status";

	public static final String SQL_CREATE_TABLE = "create table " + TABLE_NAME
			+ COLUMN_ID + " integer primary key autoincrement, " + COLUMN_TITLE
			+ " text not null, " + COLUMN_DATETIME + " datetime not null, "
			+ COLUMN_PRIORITY + " text not null, " + COLUMN_STATUS
			+ " text not null)";

	public static final String SQL_DROP_TABLE = "drop table if exists "
			+ TABLE_NAME;

}
