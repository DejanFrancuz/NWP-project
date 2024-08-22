package rs.raf.demo.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.TimeZone;
import java.util.Calendar;

public class DateUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Date convertStringToDate(String dateString) {
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return java.sql.Date.valueOf(localDate);
    }
}
