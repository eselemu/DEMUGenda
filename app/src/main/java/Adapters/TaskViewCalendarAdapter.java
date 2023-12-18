package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;
import com.example.agenda.ViewJobFragment;

import java.util.ArrayList;
import java.util.List;

import Pojo.Control;
import Pojo.Job;

import static Global.Info.currentUser;

public class TaskViewCalendarAdapter extends RecyclerView.Adapter<TaskViewCalendarAdapter.MiniActivityListJobCalendar> {
    public Context context;
    public List<Job> dateTasks = new ArrayList<>();
    public FragmentManager fragmentManager;
    @NonNull
    @Override
    public TaskViewCalendarAdapter.MiniActivityListJobCalendar onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.recycler_view_task, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        TaskViewCalendarAdapter.MiniActivityListJobCalendar obj = new TaskViewCalendarAdapter.MiniActivityListJobCalendar(rootView);
        return obj;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewCalendarAdapter.MiniActivityListJobCalendar miniActivity, int i) {
        final int position = i;
        Control control = new Control();
        Job auxJob = dateTasks.get(i);
        String auxDate = control.FormatDate(auxJob.getDay(),
                auxJob.getMonth(), auxJob.getYear());
        int drawableId;
        if(auxJob.isCompleted()) {
            drawableId = context.getResources().getIdentifier("check_icon", "drawable", context.getPackageName());
            miniActivity.txtViewTitle.setTextColor(Color.GRAY);
            miniActivity.txtViewSubject.setTextColor(Color.GRAY);
            miniActivity.txtViewDate.setTextColor(Color.GRAY);
        }
        else
            drawableId = context.getResources().getIdentifier("cross_icon", "drawable", context.getPackageName());

        miniActivity.imgViewChecked.setImageResource(drawableId);
        miniActivity.txtViewTitle.setText(auxJob.getTitle());
        miniActivity.txtViewSubject.setText(auxJob.getSubject());
        miniActivity.txtViewDate.setText(auxDate);


        miniActivity.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", auxJob.getId());
                ViewJobFragment viewJobFragment = new ViewJobFragment();
                viewJobFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, viewJobFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateTasks.size();
    }

    public class MiniActivityListJobCalendar extends RecyclerView.ViewHolder{
        public TextView txtViewTitle, txtViewSubject, txtViewDate;
        public ImageView imgViewChecked;
        public View vwSeparator;
        public MiniActivityListJobCalendar(@NonNull View itemView) {
            super(itemView);
            imgViewChecked = itemView.findViewById(R.id.imgViewIcon_ListJob);
            txtViewTitle = itemView.findViewById(R.id.txtViewTitle_ListJob);
            txtViewSubject = itemView.findViewById(R.id.txtViewSubject_ListJob);
            txtViewDate = itemView.findViewById(R.id.txtViewDate_ListJob);
            vwSeparator = itemView.findViewById(R.id.vwItemSeparator_ListJob);
        }
    }
}
