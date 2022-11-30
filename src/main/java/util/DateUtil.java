package util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date obterDiasDiferente(int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, dias);
        return calendar.getTime();
    }
}
