package com.example.healthcloud;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>{
    private ArrayList<Task> tasks = new ArrayList<>();

    public TaskRecyclerViewAdapter(ArrayList<Task> tasks) {

        this.tasks = tasks;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_task_list, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Collections.sort(this.tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {

                if(o1.getOrdertime()>o2.getOrdertime())
                    return 1;
                return -1;
            }
        });

        holder.tasktitle.setText(tasks.get(position).getTitle());
        holder.tasktype.setText(tasks.get(position).getType());
        holder.tasktimeperiod.setText(tasks.get(position).gettimeperiod());
        if (tasks.get(position).getIsrecommend()==1){
            holder.tasktitle.setTextColor(R.color.darkred);
        }

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tasktitle,tasktype,tasktimeperiod;
        private CardView parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tasktitle = itemView.findViewById(R.id.taskTitle);
            tasktype = itemView.findViewById(R.id.tasktype);
            tasktimeperiod = itemView.findViewById(R.id.tasktimeperiod);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
