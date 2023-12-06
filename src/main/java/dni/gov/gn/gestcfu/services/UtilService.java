package dni.gov.gn.gestcfu.services;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Service
public class UtilService {

    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final SimpleDateFormat def = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat def_ENG = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH);

    private static final DateTimeFormatter d = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //private static final SimpleDateFormat df_1=new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Permet de formatter une chaine de caractere au format "dd/MM/yyyy" en type Date
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static LocalDate formateDate(String dateString) {
        return LocalDate.parse(dateString, df);
    }

    public static Date formateEndDate(String dateString) {
        try {
            return def.parse(dateString);
        } catch (ParseException e) {
            return null;
        }

    }

    public static Date formateDateENG(String dateString) {
        try {
            return def_ENG.parse(dateString);
        } catch (ParseException e) {
            return null;
        }

    }

    public static String formateToStringDate(LocalDate date) {
        if (date == null) return "";
        return d.format(date);
    }

}
