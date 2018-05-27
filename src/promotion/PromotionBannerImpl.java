package promotion;

import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import loader.BannerLoader;
import loader.BannerLoaderImpl;
import pojo.Banner;
import util.DateUtils;

public class PromotionBannerImpl implements PromotionBanner {

	@Override
	public List<Banner> getBanners(InetAddress ipAddress, ZonedDateTime zonedDateTime) {
		// load all the banners
		BannerLoader bannerLoader = new BannerLoaderImpl();
		List<Banner> banners = bannerLoader.getBanners();
		System.out.println("[INFO] Total number of banners : " + banners.size());
		banners.stream().forEach(b -> b.toString());

		// filter banners which should be visible 
		List<Banner> result = banners.stream().filter(b -> {
			if (DateUtils.isBetween(zonedDateTime, b.getStartDateTime(), b.getEndDateTime())
					|| (b.getPermittedIP().contains(ipAddress) && !zonedDateTime.isAfter(b.getEndDateTime()))) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());
		System.out.println("[INFO] Number of available banners for IpAdress :" + ipAddress + " at ZonedDateTime :"
				+ zonedDateTime + " = " + result.size());
		return result;
	}

}
