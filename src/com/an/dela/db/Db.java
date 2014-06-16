package com.an.dela.db;

import android.content.Context;

public class Db {
	private static Db instance;
	private Context context;
	private OpenHelper openHelper;
	private TaskTable taskTable;

	public static Db getInstance(Context context) {
		if (null == instance)
			instance = new Db(context);
		return instance;
	}

	private Db(Context context) {
		this.context = context;
	}

	public TaskTable getTaskTable() {
		if (null == taskTable)
			taskTable = new TaskTable(getOpenHelper());
		return taskTable;
	}

	public OpenHelper getOpenHelper() {
		if (null == openHelper)
			openHelper = new OpenHelper(context);
		return openHelper;
	}

}
