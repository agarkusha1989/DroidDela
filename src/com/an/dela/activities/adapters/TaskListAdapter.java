package com.an.dela.activities.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.an.dela.R;
import com.an.dela.db.TaskRecord;

public class TaskListAdapter extends ArrayAdapter<TaskRecord> {

	private final Context context;
	private final TaskRecord[] taskRecords;

	public TaskListAdapter(Context context, TaskRecord[] records) {
		super(context, R.layout.list_task, records);
		this.context = context;
		this.taskRecords = records;
	}

	public class ViewHolder {
		public ImageView iconStatus;
		public TextView title;
		public TextView datetime;
		public TextView expires;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (null == convertView) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_task, null, true);

			holder = new ViewHolder();
			holder.iconStatus = (ImageView) convertView
					.findViewById(R.id.icon_status);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.datetime = (TextView) convertView
					.findViewById(R.id.datetime);
			holder.expires = (TextView) convertView.findViewById(R.id.expires);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		TaskRecord t = taskRecords[position];

		int priorityResColorId;
		if (t.equalPriority(TaskRecord.PRIORITY_HIGH)) {
			priorityResColorId = R.color.priority_high;
		} else if (t.equalPriority(TaskRecord.PRIORITY_MEDIUM)) {
			priorityResColorId = R.color.priority_medium;
		} else {
			priorityResColorId = R.color.priority_low;
		}

		int statusResId;
		if (t.equalStatus(TaskRecord.STATUS_NEW)) {
			statusResId = android.R.drawable.ic_input_add;
		} else if (t.equalStatus(TaskRecord.STATUS_PERFORMED)) {
			statusResId = android.R.drawable.ic_media_play;
		} else {
			statusResId = android.R.drawable.ic_media_next;
		}

		String datetime = DateFormat.format("dd.MM.yyyy kk:mm",
				t.getDateDatetime()).toString();
		String datetimeExpires = DateFormat.format("dd.MM.yyyy kk:mm",
				t.getDateDatetimeExpires()).toString();

		convertView.setBackgroundColor(context.getResources().getColor(
				priorityResColorId));
		holder.iconStatus.setImageResource(statusResId);
		holder.title.setText(t.getTitle());
		holder.datetime.setText(datetime);
		holder.expires.setText(datetimeExpires);

		return convertView;
	}

}
