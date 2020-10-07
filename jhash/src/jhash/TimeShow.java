package jhash;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

public class TimeShow  {

private SimpleDateFormat format;
private Date date;

public TimeShow(){
    format = new SimpleDateFormat("HH:mm:ss");
}

 synchronized String time () throws Throwable {
    //date = new Date();
    //format = new SimpleDateFormat("HH:mm:ss");
    sleep(700);
    return format.format(new Date());
 }

 }
