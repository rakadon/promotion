package main.java.promotion;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.java.loader.BannerLoader;
import main.java.loader.BannerLoaderImpl;
import main.java.pojo.Banner;
import main.java.pojo.PromotionOutput;
import main.java.pojo.ResultCode;
import main.java.util.DateUtil;
import main.java.util.InputValidator;


public class PromotionBannerImpl implements PromotionBanner {

	@Override
	public PromotionOutput<ResultCode,List<Banner>> getBanners(String ipAddress, String localDateTime, String zoneId) {
		PromotionOutput<ResultCode,List<Banner>> result = new PromotionOutput<ResultCode,List<Banner>>();
		result.setBanners(new ArrayList<>());
		// validate input
		if(!InputValidator.validIpAddress(ipAddress)) {
			result.setResultCode(ResultCode.INVALID_IPADDRESS);
			return result;
		}
		if(!InputValidator.validDateTime(localDateTime)) {
			result.setResultCode(ResultCode.INVALID_DATETIME);
			return result;
		}
		if(!InputValidator.validZoneId(zoneId)) {
			result.setResultCode(ResultCode.INVALID_ZONEID);
			return result;
		}
		
		// convert input localDateTime to UTC zoneDateTime
		ZonedDateTime inputZonedDateTime = DateUtil.convertToUTC(DateUtil.convertLocalToZonedDateTime(localDateTime, zoneId));
		
		// load all the banners
		BannerLoader bannerLoader = new BannerLoaderImpl();
		PromotionOutput<ResultCode,List<Banner>> allBanners = bannerLoader.getBanners();
		result.setResultCode(allBanners.getResultCode());
		// filter banners only if banners are loaded correctly from json file
		if(ResultCode.SUCCESS.equals(allBanners.getResultCode())) {
			allBanners.getBanners().stream().forEach(b -> b.toString());
			
			// filter banners which should be visible 
			List<Banner> displayBanners = allBanners.getBanners().stream().filter(b -> {
				if (DateUtil.inDuration(inputZonedDateTime, b.getStartDateTime(), b.getEndDateTime())
						|| (b.getPermittedIP().contains(ipAddress) && !inputZonedDateTime.isAfter(b.getEndDateTime()))) {
					return true;
				}
				return false;
			}).collect(Collectors.toList());
			result.setBanners(displayBanners);
		}

		return result;
	}


}
