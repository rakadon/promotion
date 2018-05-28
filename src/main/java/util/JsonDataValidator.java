package main.java.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonDataValidator {

	/**
	 * Check if the json file contains banners and is in valid format.
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static boolean checkJsonDataFile(JSONObject jsonObject) {
		JSONArray bannerArray = (JSONArray) jsonObject.get("banners");
		if (bannerArray == null || bannerArray.isEmpty()) {
			System.out.println("[ERROR] Json data file doesn't have banners or format is invalid");
			return false;
		}
		for (Object bannerObj : bannerArray) {
			if(!validateJsonObjectOfBanner((JSONObject) bannerObj)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Validate one json object of banner.
	 * 
	 * @param jsonObject
	 * @return
	 * @return
	 */
	private static boolean validateJsonObjectOfBanner(JSONObject jsonObject) {
		String bannerId = (String) jsonObject.get("bannerId");
		if (bannerId == null || bannerId.isEmpty()) {
			System.out.println("[ERROR] BannerId is empty or not found for this object : " + jsonObject.toString());
			return false;
		}
		String bannerName = (String) jsonObject.get("bannerName");
		if (bannerName == null || bannerName.isEmpty()) {
			System.out.println("[ERROR] BannerName is empty or not found for this object : " + jsonObject.toString());
			return false;
		}
		String startDateTime = (String) jsonObject.get("startDateTime");
		if (startDateTime == null || startDateTime.isEmpty()) {
			System.out.println("[ERROR] StartDateTime is empty or not found for bannerId : " + bannerId);
			return false;
		}
		String endDateTime = (String) jsonObject.get("endDateTime");
		if (endDateTime == null || endDateTime.isEmpty()) {
			System.out.println("[ERROR] EndDateTime is empty or not found for bannerId : " + bannerId);
			return false;
		}

		// check format of startDateTime and endDateTime
		try {
			DateUtil.getDateInTimezone(startDateTime, ZoneId.of("UTC"));
			DateUtil.getDateInTimezone(endDateTime, ZoneId.of("UTC"));
		} catch (DateTimeParseException e) {
			System.out.println("[ERROR] Invalid format for startDateTime or EndDateTime for bannerId : " + bannerId);
			System.out.println("[INFO] Valid format for startDateTime or EndDateTime : \"yyyy-MM-dd'T'HH:mm:ss.SSSZ\"");
			return false;
		}
		// check valid ip address
		JSONArray permittedIpArray = (JSONArray) jsonObject.get("permittedIP");
		if (permittedIpArray == null || permittedIpArray.isEmpty()) {
			System.out.println("[ERROR] permittedIp is empty or not found for bannerId : " + bannerId);
			return false;
		}else{
			for (Object ipObj : permittedIpArray) {
				try {
					InetAddress.getByName((String) ipObj);
				} catch (UnknownHostException e) {
					System.out.println("[ERROR] Invalid Ip address found for bannerId " + bannerId + " Message: "
							+ e.getMessage());
					return false;
				}
			}
		}
		return true;
	}

}
