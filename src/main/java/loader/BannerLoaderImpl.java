package main.java.loader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import main.java.pojo.Banner;
import main.java.pojo.PromotionOutput;
import main.java.pojo.ResultCode;
import main.java.util.DateUtil;
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
	private String FILE_PATH = "/main/resources/data/banners.json";

	@Override
	public PromotionOutput<ResultCode,List<Banner>> getBanners() {
		PromotionOutput<ResultCode,List<Banner>> result = new PromotionOutput<ResultCode, List<Banner>>();
		// read the banners json file and store in jsonobject
		JSONObject jsonObject = readJsonFile(FILE_PATH);
		// get the resultcode after reading json file
		ResultCode resultcode = (ResultCode) jsonObject.get("resultcode");
		result.setResultCode(ResultCode.SUCCESS);
		
		if (ResultCode.SUCCESS.equals(resultcode)) {
			result.setBanners(convertJsonToBanners(jsonObject));
		}else {
			result.setBanners(new ArrayList<>());
		}
		return result;
	}

	/**
	 * Read json file and store banners in jsonObject.
	 * 
	 * @param filePath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private JSONObject readJsonFile(String filePath) {
		JSONObject result = new JSONObject();
		try {
		    File file = new File(BannerLoaderImpl.class.getResource(filePath).getFile());
		     
		    // check if json file for banners exists
		    if(file.exists()) {
		    	JSONParser parser = new JSONParser();
				Object obj = parser.parse(new FileReader(file));
				// check if file format and data is in correct format
				if(JsonDataValidator.checkJsonDataFile((JSONObject) obj)) {
					result = (JSONObject) obj;
					result.put("resultcode", ResultCode.SUCCESS);
				}else {	            
					result.put("resultcode", ResultCode.FORMAT_ERROR);
				}
		    }else {
		    	System.out.println("[ERROR] JSON Data file for banners not found");
		    	result.put("resultcode", ResultCode.FILE_NOT_FOUND);
		    }
		} catch (IOException | org.json.simple.parser.ParseException e) {
			System.out.println("[ERROR] Cannot read or open the file : " + e.getMessage());
			result.put("resultcode", ResultCode.FILE_READ_ERROR);
		}
		return result;
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
				DateUtil.getDateInTimezone((String) jsonObject.get("startDateTime"), ZoneId.of("UTC")));
		banner.setEndDateTime(
				DateUtil.getDateInTimezone((String) jsonObject.get("endDateTime"), ZoneId.of("UTC")));
		JSONArray permittedIpArray = (JSONArray) jsonObject.get("permittedIP");
		@SuppressWarnings("unchecked")
		Stream<String> permittedIpStream = permittedIpArray.stream().map(json -> json.toString());
		List<String> permittedIpList = permittedIpStream.collect(Collectors.toList());
		banner.setPermittedIP(permittedIpList);
		return banner;
	}

}
