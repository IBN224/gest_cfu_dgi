package dni.gov.gn.gestcfu.util;

import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

public class MyFunction {

	
	public static String getPrettyDate(Date date) {
	    PrettyTime pretty = new PrettyTime();
	    return date != null ? pretty.format(date) : ""; //or whatever implementation
	}
	
	
}
