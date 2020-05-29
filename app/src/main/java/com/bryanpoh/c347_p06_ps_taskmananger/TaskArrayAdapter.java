package com.bryanpoh.c347_p06_ps_taskmananger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskArrayAdapter extends ArrayAdapter {
    private ArrayList<Task> task;
    private Context context;
    private TextView tvTop, tvBtm;

    public TaskArrayAdapter(Context context, int resource, ArrayList<Task> objects){
        super(context, resource, objects);
        task = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);

        tvTop = (TextView)rowView.findViewById(R.id.tvTop);
        tvBtm = (TextView)rowView.findViewById(R.id.tvBtm);

        Task currTask = task.get(position);

        tvTop.setText(position+1 + " " + currTask.getName());
        tvBtm.setText(currTask.getDescription());

        return rowView;

    }
}