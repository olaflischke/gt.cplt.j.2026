package seminar.modernized;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Optionale Modernisierung für fachliche Kalenderdaten. */
public class ModernOrderDateService {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.uuuu");

    public String format(LocalDate value) {
        return value.format(FORMAT);
    }

    public LocalDate nextDay(LocalDate value) {
        return value.plusDays(1);
    }
}
