package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.MainFragment;
import com.example.agenda.R;
import com.example.agenda.ViewJobFragment;

import Pojo.Control;
import Pojo.Job;

import static Global.Info.currentUser;

public class TaskViewAdapter extends RecyclerView.Adapter<TaskViewAdapter.MiniActivityListJob> {
    public Context context;
    public int firstIndexTaskFromPosteriorWeek, firstIndexTask;
    public FragmentManager fragmentManager;
    @NonNull
    @Override
    public MiniActivityListJob onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.recycler_view_task, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        MiniActivityListJob obj = new MiniActivityListJob(rootView);
        firstIndexTaskFromPosteriorWeek = currentUser.GetFirstIndexTaskFromPosteriorWeek();
        firstIndexTask = currentUser.GetFirstIndexTaskPosterior();
        return obj;
    }

    @Override
    public void onBindViewHolder(@NonNull MiniActivityListJob miniActivity, int i) {
        final int position = i;
        Control control = new Control();
        Job auxJob = currentUser.getTasks().get(i);
        String auxDate = control.FormatDate(auxJob.getDay(), auxJob.getMonth(), auxJob.getYear());
        int drawableId;
        if(currentUser.getTasks().get(i).isCompleted()) {
            drawableId = context.getResources().getIdentifier("check_icon", "drawable", context.getPackageName());
            miniActivity.txtViewTitle.setTextColor(Color.GRAY);
            miniActivity.txtViewSubject.setTextColor(Color.GRAY);
            miniActivity.txtViewDate.setTextColor(Color.GRAY);
        }
        else{
            drawableId = context.getResources().getIdentifier("cross_icon", "drawable", context.getPackageName());
            miniActivity.txtViewTitle.setTextColor(Color.BLACK);
            miniActivity.txtViewSubject.setTextColor(Color.BLACK);
            miniActivity.txtViewDate.setTextColor(Color.BLACK);
        }

        miniActivity.imgViewChecked.setImageResource(drawableId);
        miniActivity.txtViewTitle.setText(auxJob.getTitle());
        miniActivity.txtViewSubject.setText(auxJob.getSubject());
        miniActivity.txtViewDate.setText(auxDate);

        if(i == firstIndexTaskFromPosteriorWeek || i == firstIndexTask)
            miniActivity.vwSeparator.setVisibility(View.VISIBLE);
        else
            miniActivity.vwSeparator.setVisibility(View.INVISIBLE);

        miniActivity.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        return currentUser.getTasks().size();
    }

    public class MiniActivityListJob extends RecyclerView.ViewHolder{
        public TextView txtViewTitle, txtViewSubject, txtViewDate;
        public ImageView imgViewChecked;
        public View vwSeparator;
        public MiniActivityListJob(@NonNull View itemView) {
            super(itemView);
            imgViewChecked = itemView.findViewById(R.id.imgViewIcon_ListJob);
            txtViewTitle = itemView.findViewById(R.id.txtViewTitle_ListJob);
            txtViewSubject = itemView.findViewById(R.id.txtViewSubject_ListJob);
            txtViewDate = itemView.findViewById(R.id.txtViewDate_ListJob);
            vwSeparator = itemView.findViewById(R.id.vwItemSeparator_ListJob);
        }
    }
}
