package com.an.db;

import java.util.HashMap;

public abstract class Record {
	private HashMap<String, String> strings = new HashMap<String, String>();
	private HashMap<String, Integer> integers = new HashMap<String, Integer>();
	private HashMap<String, Boolean> booleans = new HashMap<String, Boolean>();
	private HashMap<String, Double> doubles = new HashMap<String, Double>();

	public String getString(String name, String defaultValue) {
		if (strings.containsKey(name))
			return strings.get(name);
		else
			return defaultValue;
	}

	public int getInt(String name, int defaultValue) {
		if (integers.containsKey(name))
			return integers.get(name);
		else
			return defaultValue;
	}

	public boolean getBoolean(String name, boolean defaultValue) {
		if (booleans.containsKey(name))
			return booleans.get(name);
		else
			return defaultValue;
	}

	public boolean hasString(String name) {
		return strings.containsKey(name);
	}

	public boolean hasInt(String name) {
		return integers.containsKey(name);
	}

	public boolean hasBoolean(String name) {
		return booleans.containsKey(name);
	}

	public boolean hasDouble(String name) {
		return doubles.containsKey(name);
	}

	public double getDouble(String name, double defaultValue) {
		if (booleans.containsKey(name))
			return doubles.get(name);
		else
			return defaultValue;
	}

	public Record set(String name, String value) {
		strings.put(name, value);
		return this;
	}

	public Record set(String name, int value) {
		integers.put(name, value);
		return this;
	}

	public Record set(String name, boolean value) {
		booleans.put(name, value);
		return this;
	}

	public Record set(String name, double value) {
		doubles.put(name, value);
		return this;
	}
}
