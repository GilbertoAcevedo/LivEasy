package cse110.liveasy;

/**
 * Created by ira on 11/18/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.app.Activity;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class CalendarActivity extends Activity {
    CalendarView calendar;

    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the main layout of the activity
        setContentView(R.layout.calendar);

        //initializes the calendarview
        initializeCalendar();
    }

    public void setTime(int day, int month, int year)
    {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay()
    {
        return day;
    }

    public void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendar);

        calendar.setOnDateChangeListener(new OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day)
            {

                setTime(day, month, year);
                setContentView(R.layout.add_event_popup);

                Spinner dropdown = (Spinner)findViewById(R.id.month_spinner);
                String[] items = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                        "Aug", "Sept", "Oct", "Nov", "Dec"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item, items);
                dropdown.setAdapter(adapter);
            }
        });
    }

    public void goToCalendar( View view ) {

        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}