package com.example.agenda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Adapters.CalendarGridAdapter;
import Adapters.TaskViewAdapter;
import Adapters.TaskViewCalendarAdapter;
import Pojo.Control;
import Pojo.Job;
import Pojo.JobDAO;

import static Global.Info.currentUser;
import static Global.Info.stringSubjects;

public class CustomCalendarView extends LinearLayout {

    ImageButton nextButton, prevButton;
    TextView monthYearText;
    GridView gridView;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;

    SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat englishFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);

    List<Date> dates = new ArrayList<>();

    CalendarGridAdapter calendarGridAdapter;
    AlertDialog alertDialog;

    String selectedSubject;

    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Control control = new Control();
        initializeLayout();
        setUpCalendar();
        control.UpdateStringSubjects();

        prevButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, -1);
                setUpCalendar();
            }
        });

        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, 1);
                setUpCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String date = control.simpleDateFormat.format(dates.get(i));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View showView = LayoutInflater.from(adapterView.getContext()).inflate(R.layout.show_events_layout, null);
                RecyclerView rv = showView.findViewById(R.id.RVCalendarEvents_ShowEventsLayout);
                TaskViewCalendarAdapter taskViewCalendarAdapter = new TaskViewCalendarAdapter();
                taskViewCalendarAdapter.context = getContext();
                taskViewCalendarAdapter.dateTasks = currentUser.GetTasksByDate(date);
                taskViewCalendarAdapter.fragmentManager =((AppCompatActivity) context).getSupportFragmentManager();

                LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rv.setLayoutManager(llm);
                rv.setAdapter(taskViewCalendarAdapter);

                builder.setView(showView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
         gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                 String stringDate = englishFormat.format(dates.get(i));
                 EditText taskEdtText, descriptionEdtText;
                 Spinner subjectSpinner;
                 Button addTaskBtn;

                 LocalDate date = LocalDate.parse(stringDate);

                 Date d = dates.get(i);

                 AlertDialog.Builder builder = new AlertDialog.Builder(context);
                 builder.setCancelable(true);
                 View addView = LayoutInflater.from(adapterView.getContext()).inflate(R.layout.add_events_layout, null);

                 taskEdtText = addView.findViewById(R.id.editTextTaskTitle_AddEventLayout);
                 descriptionEdtText = addView.findViewById(R.id.editTextTaskDescription_AddEventLayout);
                 subjectSpinner = addView.findViewById(R.id.spinnerTaskSubject_AddEventLayout);
                 addTaskBtn = addView.findViewById(R.id.btnAddTask_AddEventLayout);

                 ArrayAdapter<String> adapterSubjectSpinner = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, stringSubjects);
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

                 addTaskBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         JobDAO jobDAO = new JobDAO();
                         String title = taskEdtText.getText().toString().trim();
                         String description = descriptionEdtText.getText().toString().trim();
                         jobDAO.CreateJob(title, description, selectedSubject, date.getYear(), date.getMonthValue(), date.getDayOfMonth(), context);
                     }
                 });

                 builder.setView(addView);
                 alertDialog = builder.create();
                 alertDialog.show();
                 return true;
             }
         });
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initializeLayout()
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        nextButton = findViewById(R.id.buttonNextMonth_CalendarLayout);
        prevButton = findViewById(R.id.buttonPrevMonth_CalendarLayout);
        monthYearText = findViewById(R.id.txtViewMY_CalendarLayout);
        gridView = findViewById(R.id.gridView_CalendatLayout);
    }

    private void setUpCalendar()
    {
        int firstDayOfMonth;
        String monthYearS = monthYearFormat.format(calendar.getTime());
        monthYearText.setText(monthYearS);
        dates.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        while(dates.size() <  MAX_CALENDAR_DAYS)
        {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        calendarGridAdapter = new CalendarGridAdapter(context, dates, calendar);
        gridView.setAdapter(calendarGridAdapter);
    }
}
