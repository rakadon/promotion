package util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class DateUtils {

	//private static String ZONED_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	/**
	 * Convert zonedIsoDateString to given zoneId's ZonedDateTime
	 * 
	 * @param dateString
	 * @param zoneId
	 * @return
	 */
	public static ZonedDateTime getDateInTimezone(String dateString, ZoneId zoneId) throws DateTimeParseException{
		ZonedDateTime dateTime = null;
		Instant instant = Instant.parse(dateString);
		dateTime = instant.atZone(zoneId);
		return dateTime;
	}

	/**
	 * Return true if zt1 comes in between zt2 and zt3 (zt2<=zt1<=zt3)
	 * 
	 * @param zt1
	 * @param zt2
	 * @param zt3
	 * @return
	 */
	public static boolean isBetween(ZonedDateTime zt1, ZonedDateTime zt2, ZonedDateTime zt3) {
		return (zt1.isAfter(zt2) || zt1.isEqual(zt2)) && (zt1.isBefore(zt3) || zt1.isEqual(zt3));
	}

}
