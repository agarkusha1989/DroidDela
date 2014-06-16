package com.an.dela.db;

import com.an.db.Record;

public class TaskRecord extends Record {

	public static final int PRIORITY_URGENT = 0;
	public static final int PRIORITY_HIGH = 1;
	public static final int PRIORITY_MEDIUM = 2;
	public static final int PRIORITY_LOW = 3;
	
	public static final int STATUS_NEW = 0;
	public static final int STATUS_PERFORMED = 1;
	public static final int STATUS_COMPLETED = 2;
	
}
