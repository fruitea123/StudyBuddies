package use_case.calendar;

import com.google.api.services.calendar.Calendar;
import data_access.GoogleCalendarConfig;
import data_access.GoogleCalendarServiceImpl;
import use_case.calendar.CalendarService;

public class CalendarController {
    public CalendarService buildCalendarService() throws Exception {
        Calendar googleCalendar = GoogleCalendarConfig.getCalendarService();
        return new GoogleCalendarServiceImpl(googleCalendar);
    }
}
