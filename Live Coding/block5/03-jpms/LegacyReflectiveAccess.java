package seminar.legacy;

import java.lang.reflect.Field;

/** Diagnosebeispiel für nicht unterstützte Reflection auf JDK-Interna. */
public class LegacyReflectiveAccess {
    public static void inspectString(String value) throws Exception {
        Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);
        Object internalValue = field.get(value);
        System.out.println(internalValue.getClass().getName());
    }
}
