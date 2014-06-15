package com.an.db;

import android.content.ContentValues;

public interface Table<K, R> {
	public R select(K key);
	public R[] select();
	public R insert(R record);
	public R[] insert(R[] records);
	public R update(R record);
	public R[] update(R[] records);
	public void delete(R record);
	public void delete(R[] records);
	public void clear(); 
}
