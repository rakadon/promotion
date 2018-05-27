package main.java.processor;

import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import main.java.loader.BannerLoader;
import main.java.loader.BannerLoaderImpl;
import main.java.pojo.Banner;
import main.java.util.DateUtils;

public class PromotionBannerImpl implements PromotionBanner {

	@Override
	public List<Banner> getBanners(InetAddress ipAddress, ZonedDateTime zonedDateTime) {
		BannerLoader bannerLoader = new BannerLoaderImpl();
		List<Banner> banners = bannerLoader.getBanners();
		System.out.println("[INFO] Total number of banners : " + banners.size());
		banners.stream().forEach(b -> b.toString());

		List<Banner> result = banners.stream().filter(b -> {
			if (DateUtils.isBetween(zonedDateTime, b.getStartDateTime(), b.getEndDateTime())
					|| (b.getPermittedIP().contains(ipAddress) && !zonedDateTime.isAfter(b.getEndDateTime()))) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());
		System.out.println("[INFO] Number of available banners for IpAdress :" + ipAddress + ", ZonedDateTime :"
				+ zonedDateTime + " = " + result.size());
		return result;
	}

}
