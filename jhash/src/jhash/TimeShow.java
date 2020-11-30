package jhash;

import java.text.SimpleDateFormat;
import java.util.Date;
import static java.lang.Thread.sleep;

public class TimeShow  {

private final SimpleDateFormat format;

public TimeShow(){
    format = new SimpleDateFormat("HH:mm:ss");
}

 synchronized String time () throws Throwable {
    sleep(700);
    return format.format(new Date());
 }

 }
