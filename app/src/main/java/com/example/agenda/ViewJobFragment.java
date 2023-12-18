package com.example.agenda;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import Pojo.Control;
import Pojo.Job;
import Pojo.JobDAO;

import static Global.Info.currentUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewJobFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewJobFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button buttonDelete, buttonEdit;
    TextView textViewTask, textViewDescription, textViewSubject, textViewDate;
    CheckBox checkBoxCompleted;

    View view;

    public ViewJobFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewJobFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewJobFragment newInstance(String param1, String param2) {
        ViewJobFragment fragment = new ViewJobFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_view_job, container, false);

        textViewTask = view.findViewById(R.id.txtViewTask_ViewJob);
        textViewDescription = view.findViewById(R.id.txtViewDescription_ViewJob);
        textViewSubject = view.findViewById(R.id.txtViewSubject_ViewJob);
        textViewDate = view.findViewById(R.id.txtViewDate_ViewJob);

        buttonDelete = view.findViewById(R.id.btnDelete_ViewJob);

        checkBoxCompleted = view.findViewById(R.id.checkBoxCompleted_ViewJob);

        int position = currentUser.GetIndexById(getArguments().getString("id"));

        Job auxJob = currentUser.getTasks().get(position);
        Control control = new Control();

        if(position != -1)
        {
            textViewTask.setText("Task: " + auxJob.getTitle());
            textViewDescription.setText("Description: " + auxJob.getDescription());
            textViewSubject.setText("Subject: " + auxJob.getSubject());
            textViewDate.setText(control.FormatDate(auxJob.getDay(), auxJob.getMonth(), auxJob.getYear()));
            checkBoxCompleted.setChecked(auxJob.isCompleted());
        }

        checkBoxCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobDAO jobDAO = new JobDAO();
                jobDAO.UpdateCompletedJob(auxJob.getId(), checkBoxCompleted.isChecked(), view.getContext());
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobDAO jobDAO = new JobDAO();
                jobDAO.DeleteJob(auxJob.getId(), view.getContext());
            }
        });

        return view;
    }
}