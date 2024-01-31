package com.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class RiaDateTimeParser implements DateTimeParser{
    public Date parse(String date) {
        //var patternFullDate = Pattern.compile("\\d\\d:\\d\\d,\\s\\d{1,2}\\s\\D+\\s\\d{4}");
        var patternToday = Pattern.compile("^\\d\\d:\\d\\d$");
        var patternYesterday = Pattern.compile("Вчера,\\s\\d\\d:\\d\\d");
        var patternDateWOYear = Pattern.compile("\\d{1,2}\\s\\D+,\\s\\d\\d:\\d\\d");
        
        var mather = patternToday.matcher(date);
        if (mather.find()) {
            var calendar = Calendar.getInstance();
            this.setTime(calendar, date);
            return calendar.getTime();
        }
        mather = patternYesterday.matcher(date);
        if (mather.find()) { 
            var calendar = Calendar.getInstance();
            this.setTimeYesterday(calendar, date);
            return calendar.getTime();
        }
        mather = patternDateWOYear.matcher(date);
        if (mather.find()) {
            return this.parseDateTimeWOYear(date);
        }
        else {
            return this.parseDateTimeWYear(date);
        }
    }

    private void setTimeYesterday(Calendar calendar, String parsedTime) {
        var str = parsedTime.split(" ");
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        this.setTime(calendar, str[1]);
    }

    private void setTime(Calendar calendar, String parsedTime) {
        var str = parsedTime.split(":");
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(str[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(str[1]));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private Date parseDateTimeWOYear(String datetime) {
        SimpleDateFormat parser = new SimpleDateFormat("dd MMMM, HH:mm"
            , new Locale.Builder().setLanguage("ru").setScript("Cyrl").build());
        parser.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        try {
            Date date = parser.parse(datetime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.YEAR, Year.now().getValue());
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Date parseDateTimeWYear(String datetime) {
        SimpleDateFormat parser = new SimpleDateFormat("dd MMMM yyyy, HH:mm"
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
