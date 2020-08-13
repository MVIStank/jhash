package jhash;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeShow  {

private SimpleDateFormat format;
private Date date;

 String time (){

    date = new Date();
    format = new SimpleDateFormat("HH:mm:ss");
    return format.format(date);
 }

 }
