package main.java.loader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import main.java.pojo.Banner;
import main.java.util.DateUtils;
import main.java.util.JsonDataValidator;


/**
 * Class to load banners from json file.
 * 
 * @author raju
 *
 */
public class BannerLoaderImpl implements BannerLoader {

	/**
	 * Static relative path from where banners json file will be loaded
	 */
	//private String FILE_PATH = "/home/raju/eclipse-workspace/Promotion/bin/data/banners.json";
	private String FILE_PATH = "/main/resource/data/banners.json";

	@Override
	public List<Banner> getBanners() {
		// read the banners json file and store in jsonobject
		JSONObject jsonObject = readJsonFile(FILE_PATH);

		if (jsonObject != null) {
			// convert json data to banner pojo
			return convertJsonToBanners(jsonObject);
		}
		return new ArrayList<>();
	}

	/**
	 * Read json file and store banners in jsonObject.
	 * 
	 * @param filePath
	 * @return
	 */
	private JSONObject readJsonFile(String filePath) {
		try {
		    File file = new File(BannerLoaderImpl.class.getResource(filePath).getFile());
		     
		    // check if json file for banners exists
		    if(file.exists()) {
		    	JSONParser parser = new JSONParser();
				Object obj = parser.parse(new FileReader(file));
				// check if file format and data is correct
				if(JsonDataValidator.checkJsonDataFile((JSONObject) obj)) {
					return (JSONObject) obj;
				}	            
		    }else {
		    	System.out.println("[ERROR] JSON Data file for banners not found");
				return null;
		    }
		} catch (IOException | org.json.simple.parser.ParseException e) {
			System.out.println("[ERROR] Cannot read or open the file : " + e.getMessage());
		}
		return null;
		
	}

	/**
	 * Convert jsonObject to List of Banner pojo's
	 * 
	 * @param jsonObject
	 * @return
	 */
	private List<Banner> convertJsonToBanners(JSONObject jsonObject) {
		JSONArray bannerArray = (JSONArray) jsonObject.get("banners");
		//convert each json object of banner to banner pojo 
		@SuppressWarnings("unchecked")
		Stream<Banner> bannerStream = bannerArray.stream().map(
				json -> convertJsonObjectToBanner((JSONObject) json));
		List<Banner> bannerList = bannerStream.collect(Collectors.toList());
		return bannerList;
	}

	/**
	 * Convert a jsonObject to Banner class
	 * 
	 * @param jsonObject
	 * @return
	 */
	private Banner convertJsonObjectToBanner(JSONObject jsonObject) {
		Banner banner = new Banner();
		banner.setBannerId((String) jsonObject.get("bannerId"));
		banner.setBannerName((String) jsonObject.get("bannerName"));
		banner.setStartDateTime(
				DateUtils.getDateInTimezone((String) jsonObject.get("startDateTime"), ZoneId.of("UTC")));
		banner.setEndDateTime(
				DateUtils.getDateInTimezone((String) jsonObject.get("endDateTime"), ZoneId.of("UTC")));
		JSONArray permittedIpArray = (JSONArray) jsonObject.get("permittedIP");
		@SuppressWarnings("unchecked")
		Stream<String> permittedIpStream = permittedIpArray.stream().map(json -> json.toString());
		List<InetAddress> permittedIpList = permittedIpStream.map(ip -> {
			try {
				return InetAddress.getByName(ip);
			} catch (UnknownHostException e) {
				System.out.println("[ERROR] Invalid Ip address in JSON file for BannerId "
			+ banner.getBannerId() + " Message: " + e.getMessage());
			}
			return null;
		}).collect(Collectors.toList());
		banner.setPermittedIP(permittedIpList);
		return banner;
	}

}
