package test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import pojo.Banner;
import promotion.PromotionBanner;
import promotion.PromotionBannerImpl;
import util.DateUtils;

public class TestPromotion {

    public static void main(String[] args) throws UnknownHostException {
	PromotionBanner promotionBanner = new PromotionBannerImpl();

	if (test1(promotionBanner)) {
	    System.out.println("Test1 : Passed");
	}

    }

    private static boolean test1(PromotionBanner promotionbanner)
	    throws UnknownHostException {
	List<InetAddress> ipaddresslist = new ArrayList<>();
	ipaddresslist.add(InetAddress.getByName("10.0.0.1"));
	ipaddresslist.add(InetAddress.getByName("10.0.0.2"));

	List<Banner> expected = new ArrayList<>();
	expected.add(new Banner("1", "A",
		DateUtils.getDateInTimezone("2018-06-01t01:00:00.000z",
			ZoneId.of("UTC")),
		DateUtils.getDateInTimezone("2018-06-01t23:59:00.000z",
			ZoneId.of("UTC")),
		ipaddresslist));
	expected.add(new Banner("2", "B",
		DateUtils.getDateInTimezone("2018-06-01t01:00:00.000z",
			ZoneId.of("UTC")),
		DateUtils.getDateInTimezone("2018-06-01t23:59:00.000z",
			ZoneId.of("UTC")),
		ipaddresslist));

	List<Banner> result = promotionbanner.getBanners(
		InetAddress.getByName("10.0.0.1"), DateUtils.getDateInTimezone(
			"2018-06-01t01:00:00.000z", ZoneId.of("UTC")));

	if (!expected.equals(result)) {
	    System.out.println("Test1 : Failed");
	    System.out.println("Expected output :");
	    expected.stream().forEach(i -> System.out.println(i.toString()));
	    System.out.println("Result output :");
	    result.stream().forEach(i -> System.out.println(i.toString()));
	    return false;
	}
	return true;
    }

}
