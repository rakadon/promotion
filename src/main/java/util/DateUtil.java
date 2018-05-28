package main.java.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

	/**
	 * Convert zonedIsoDateString to given zoneId's ZonedDateTime
	 * 
	 * @param dateString
	 * @param zoneId
	 * @return
	 */
	public static ZonedDateTime getDateInTimezone(String dateString, ZoneId zoneId) throws DateTimeParseException {
		ZonedDateTime dateTime = null;
		Instant instant = Instant.parse(dateString);
		dateTime = instant.atZone(zoneId);
		return dateTime;
	}

	/**
	 * Function to check user's zonedDateTime is in banner's display duration.
	 * Return true if input comes in duration of zt2 and zt3 (zt2<=input<zt3)
	 * 
	 * @param zt1
	 *            String
	 * @param zt2
	 *            ZonedDateTime
	 * @param zt3
	 *            ZonedDateTime
	 * @return
	 */
	public static boolean inDuration(ZonedDateTime input, ZonedDateTime zt1, ZonedDateTime zt2) {
		return (input.isAfter(zt1) || input.isEqual(zt1)) && input.isBefore(zt2);
	}

	public static ZonedDateTime convertLocalToZonedDateTime(String localDateTime, String zoneId) {
		return ZonedDateTime.of(LocalDateTime.parse(localDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME), ZoneId.of(zoneId));
	}

	public static ZonedDateTime convertToUTC(ZonedDateTime zoneDateTime) {
		return zoneDateTime.withZoneSameInstant(ZoneOffset.UTC);
	}

}
