package galaga_game;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyResourceBundle {
	 private static ResourceBundle instance = null;
	 private static Locale locale = null;
	 private static DateTimeFormatter dateTimeFormatter = null;

	 private static void createBundle() {
		String language = System.getProperty("language");
		String country = System.getProperty("country");

		if(language != null){
			if(country != null){
				locale = new Locale(language,country);
			}
			else {
				locale = new Locale(language);
			}
		}
		else {
			locale = Locale.getDefault();
		}
		
		dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale);
		instance = ResourceBundle.getBundle("Texts", locale);
	 }

    public static ResourceBundle getBundle() {
        if(instance == null) {
        	createBundle();
        }
    	return instance;
    }
    
    public static Locale getLocale() {
        if(locale == null) {
        	createBundle();
        }
    	return locale;
    }
    
    public static DateTimeFormatter getDateTimeFormatter() {
    	if(dateTimeFormatter == null) {
    		createBundle();
    	}
    	return dateTimeFormatter;
    }
    
    public static void setLanguage(String language) {
    	locale = new Locale(language);
    	dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale);
		instance = ResourceBundle.getBundle("Texts", locale);
    }
}
