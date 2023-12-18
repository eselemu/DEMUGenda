package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;
import com.example.agenda.ViewJobFragment;

import Pojo.Control;
import Pojo.Job;
import Pojo.Subject;
import Pojo.SubjectDAO;

import static Global.Info.currentUser;
import static Global.Info.stringSubjects;

public class RvDeleteSubjectAdapter extends RecyclerView.Adapter<RvDeleteSubjectAdapter.MiniActivityDeleteSubject> {
    public Context context;
    @NonNull
    @Override
    public RvDeleteSubjectAdapter.MiniActivityDeleteSubject onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.recycler_delete_subject, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        RvDeleteSubjectAdapter.MiniActivityDeleteSubject obj = new RvDeleteSubjectAdapter.MiniActivityDeleteSubject(rootView);
        Control control = new Control();
        control.UpdateStringSubjects();
        return obj;
    }

    @Override
    public void onBindViewHolder(@NonNull MiniActivityDeleteSubject miniActivity, int i) {
        int position = i;
        miniActivity.txtViewSubject.setText(stringSubjects.get(i));

        // Create a final variable to hold the position value
        final int currentPosition = i;

        miniActivity.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubjectDAO subjectDAO = new SubjectDAO();
                String subId = currentUser.getSubjects().get(currentPosition).getId();
                subjectDAO.DeleteSubject(subId, context);
                stringSubjects.remove(currentPosition);
                currentUser.DeleteSubjectById(subId);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringSubjects.size();
    }

    public class MiniActivityDeleteSubject extends RecyclerView.ViewHolder{
        public TextView txtViewSubject;
        public ImageButton imageButtonDelete;
        public MiniActivityDeleteSubject(@NonNull View itemView) {
            super(itemView);
            txtViewSubject = itemView.findViewById(R.id.txtViewSubject_DeleteSubject);
            imageButtonDelete = itemView.findViewById(R.id.imgButtonDelete_DeleteSubject);
        }
    }
}

