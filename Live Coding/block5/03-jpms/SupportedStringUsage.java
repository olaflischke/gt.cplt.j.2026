package seminar.modernized;

/** Öffentliche APIs statt interner JDK-Implementierung verwenden. */
public class SupportedStringUsage {
    public static int characterCount(String value) {
        return value.length();
    }
}
