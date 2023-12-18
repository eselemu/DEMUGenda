package com.example.agenda;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Pojo.Control;
import Pojo.JobDAO;
import Pojo.SubjectDAO;

import static Global.Info.currentUser;
import static Global.Info.stringSubjects;

public class AddJobFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AlertDialog alertDialog;

    EditText taskEdtText, descriptionEdtText;
    Spinner subjectSpinner;
    CalendarView taskDateCalView;
    Button addTaskBtn;
    ImageButton addSubjectBtn;
    View view;

    String selectedSubject;
    int year, month, day;

    public AddJobFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddJobFragment newInstance(String param1, String param2) {
        AddJobFragment fragment = new AddJobFragment();
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
        view = inflater.inflate(R.layout.fragment_add_job, container, false);
        taskEdtText = view.findViewById(R.id.editTextTaskTitle_AddJob);
        descriptionEdtText = view.findViewById(R.id.editTextTaskDescription_AddJob);
        subjectSpinner = view.findViewById(R.id.spinnerTaskSubject_AddJob);
        taskDateCalView = view.findViewById(R.id.calendarViewTaskDate_AddJob);
        addTaskBtn = view.findViewById(R.id.btnAddTask_AddJob);
        addSubjectBtn = view.findViewById(R.id.imgButAddSubject_AddJob);

        Control control = new Control();
        control.UpdateStringSubjects();

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        day = today.monthDay;
        month = today.month + 1;
        year = today.year;

        ArrayAdapter<String> adapterSubjectSpinner = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, stringSubjects);
        subjectSpinner.setAdapter(adapterSubjectSpinner);

        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedSubject = stringSubjects.get(0);
            }
        });

        taskDateCalView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int mYear, int mMonth, int mDay) {
                year = mYear;
                month = mMonth + 1;
                day = mDay;
            }
        });

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobDAO jobDAO = new JobDAO();
                String title = taskEdtText.getText().toString().trim();
                String description = descriptionEdtText.getText().toString().trim();
                jobDAO.CreateJob(title, description, selectedSubject, year, month, day, getActivity());
            }
        });

        addSubjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText subjectEditText;
                Button uploadSubjectBtn;

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                View addSubjectView = LayoutInflater.from(view.getContext()).inflate(R.layout.add_subjects_layout, null);

                subjectEditText = addSubjectView.findViewById(R.id.editTextSubject_AddSubjectsLayout);
                uploadSubjectBtn = addSubjectView.findViewById(R.id.btnAddSubject_AddSubjectsLayout);

                uploadSubjectBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String subject = subjectEditText.getText().toString().trim();
                        SubjectDAO subjectDAO = new SubjectDAO();
                        subjectDAO.CreateSubject(subject, getContext());
                        stringSubjects.add(subject);
                    }
                });

                builder.setView(addSubjectView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}