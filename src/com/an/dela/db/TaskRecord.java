package com.an.dela.db;

import com.an.db.Record;

public class TaskRecord extends Record {

	public enum Priority {
		URGENT, HIGH, MEDIUM, LOW;
	};

	public enum Status {
		NEW, PERFORMED, COMPLETED;
	}

}
