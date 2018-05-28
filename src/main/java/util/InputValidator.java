package main.java.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class InputValidator {

	/**
	 * Check input ipadress is valid ipaddress
	 * 
	 * @param ipaddress
	 * @return
	 */
	public static boolean validIpAddress(String ipaddress) {
		try {
			InetAddress.getByName(ipaddress);
		} catch (UnknownHostException e) {
//			System.out.println("[ERROR] invalid input IpAddress : " + e.getMessage());
			return false;
		}
		return true;

	}

	/**
	 * Check input zoneDateTime is valid and in format "yyyy-MM-dd'T'HH:mm:ss"
	 * 
	 * @param zondeDateTime
	 * @return
	 */
	public static boolean validDateTime(String dateTime) {
		try {
			LocalDateTime localtDateAndTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		} catch (DateTimeParseException e) {
//			System.out.println("[ERROR] invalid input dateTime : " + e.getMessage());
			return false;
		}

		return true;
	}
	
	/**
	 * Check ZoneId is valid or not
	 * 
	 * @param zondeId
	 * @return
	 */
	public static boolean validZoneId(String zondeId) {
		return ZoneId.getAvailableZoneIds().contains(zondeId);
	}

}
