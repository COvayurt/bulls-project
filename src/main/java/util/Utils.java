package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static Date isDateWithFormatReturnDate(String value, String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			Date date = df.parse(value);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean isDateWithFormat(String value, String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			df.parse(value);
			return Boolean.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}
	
	public static boolean isAmountShareValue(String amount){
			try {
				String[] amountSplit = amount.split("[.]");
				if(amountSplit[1].length() > 2){
					return Boolean.TRUE;
				}else{
					return Boolean.FALSE;
				}
			} catch (Exception e) {
				return Boolean.FALSE;
			}
	}
	
	public static boolean isAmountShareCapitalValue(String amount){
		try {
			String[] amountSplit = amount.split("[.]");
			if(amountSplit[1].length() <= 2){
				return Boolean.TRUE;
			}else{
				return Boolean.FALSE;
			}
		} catch (Exception e) {
			return Boolean.FALSE;
		}
}
	
}
