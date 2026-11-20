package seminar.legacy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** Kontrastbeispiel: durch Java 17 nicht entfernt. */
public class LegacyOrderDateService {
    public String format(Date value) {
        return new SimpleDateFormat("dd.MM.yyyy").format(value);
    }

    public Date nextDay(Date value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
}
