package Pojo;

import android.util.Log;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static Global.Info.currentUser;
import static Global.Info.stringSubjects;

public class Control {

    public SimpleDateFormat simpleDateFormat;
    public Control()
    {
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public String FormatDate(int day, int month, int year) {
        Date date = null;
        try {
            date = simpleDateFormat.parse(day + "/" + month + "/" + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return simpleDateFormat.format(date);
    }

    public boolean IsDatePosterior(int day, int month, int year) {
        Date today = new Date();
        try {
            today = simpleDateFormat.parse(simpleDateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = null;
        try {
            date = simpleDateFormat.parse(day + "/" + month + "/" + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date.compareTo(today) >= 0);
    }

    public boolean IsPreviousDate(int day, int month, int year)
    {
        Date today = new Date();
        Date date = null;
        try {
            date = simpleDateFormat.parse(day + "/" + month + "/" + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date.compareTo(today) < 0);
    }

    public boolean IsDateFromPosteriorWeek(int day, int month, int year)
    {
        Calendar week = Calendar.getInstance(), date = Calendar.getInstance();
        week.add(Calendar.WEEK_OF_YEAR, 1);
        date.set(year, month-1, day);
        return (date.after(week));
    }

    public void UpdateStringSubjects()
    {
        stringSubjects.clear();
        for(Subject subject : currentUser.getSubjects())
            stringSubjects.add(subject.getSubject());
    }
}
