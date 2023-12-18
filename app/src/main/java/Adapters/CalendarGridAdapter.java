package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.agenda.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static Global.Info.currentUser;

public class CalendarGridAdapter extends ArrayAdapter {

    List<Date> dates;
    Calendar currentDate;
    LayoutInflater inflater;

    public CalendarGridAdapter(@NonNull Context context, List<Date> dates, Calendar currentDate) {
        super(context, R.layout.single_cell_layout);
        this.dates = dates;
        this.currentDate = currentDate;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int tasksCounter = currentUser.GetQuantityPendingTasksByDate(dayNo, displayMonth, displayYear);

        View view = convertView;

        if(view == null)
            view = inflater.inflate(R.layout.single_cell_layout, parent, false);

        TextView dayNumberSingleCell = view.findViewById(R.id.txtViewDay_SingleCellLayout);
        TextView numberEventsSingleCell = view.findViewById(R.id.txtViewNumEvents_SingleCellLayout);
        ImageView circleNumberEventsSingleCell = view.findViewById(R.id.imgViewNumEvents_SingleCellLayout);
        dayNumberSingleCell.setText(String.valueOf(dayNo));
        if(tasksCounter > 0){
            numberEventsSingleCell.setText(String.valueOf(tasksCounter));
            circleNumberEventsSingleCell.setVisibility(View.VISIBLE);
        }

        if(displayMonth == currentMonth && displayYear == currentYear) {
            view.setBackgroundColor(Color.parseColor("#9FA4E0"));
            dayNumberSingleCell.setTextColor(Color.WHITE);
        }

        if(displayMonth == currentMonth && displayYear == currentYear && displayDay == currentDay){
            view.setBackgroundColor(Color.parseColor("#dcbfe3"));
        }

        return view;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
