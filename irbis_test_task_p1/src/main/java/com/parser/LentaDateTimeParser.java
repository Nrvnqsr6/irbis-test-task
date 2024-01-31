package com.parser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class LentaDateTimeParser implements DateTimeParser {
    public Date parse(String datetime) {
        //var pattern = Pattern.compile("\\d\\d:\\d\\d,\\s\\d{1,2}\\s\\D+\\s\\d{4}");
        var pattern = Pattern.compile("^\\d\\d:\\d\\d$");
        var mather = pattern.matcher(datetime);
        if (mather.find()) {
            var calendar = Calendar.getInstance();
            this.setTime(calendar, datetime);
            return calendar.getTime();
        }
        else {
            return this.parseDateTime(datetime);
        }
    }

    private void setTime(Calendar calendar, String parsedTime) {
        var str = parsedTime.split(":");
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(str[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(str[1]));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private Date parseDateTime(String datetime) {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm, dd MMMM yyyy"
            , new Locale.Builder().setLanguage("ru").setScript("Cyrl").build());
        parser.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        
        try {
            Date date = parser.parse(datetime);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
