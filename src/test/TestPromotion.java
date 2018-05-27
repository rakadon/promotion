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
	    System.out.println("##############################");
	}
	if (test2(promotionBanner)) {
	    System.out.println("Test2 : Passed");
	    System.out.println("##############################");
	}

    }
    
    /**
     * Input : Query from 10.0.0.1 to get valid banners at "2018-06-01t01:00:00.000z"
     * Output : There are 2 banner which are active at "2018-06-01t01:00:00.000z"
     *
     */
    private static boolean test1(PromotionBanner promotionbanner)
	    throws UnknownHostException {
	// Query from 10.0.0.1 to get valid banners at "2018-06-01t01:00:00.000z"
	List<Banner> result = promotionbanner.getBanners(
		InetAddress.getByName("10.0.0.1"), DateUtils.getDateInTimezone(
			"2018-06-01t01:00:00.000z", ZoneId.of("UTC")));
	
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

	if (!expected.equals(result)) {
	    System.out.println("Test1 : Failed");
	    System.out.println("Expected output :");
	    expected.stream().forEach(i -> System.out.println(i.toString()));
	    System.out.println("Result output :");
	    result.stream().forEach(i -> System.out.println(i.toString()));
	    System.out.println("##############################");
	    return false;
	}
	return true;
    }
    
    /**
     * The banner should be displayed to permitted IP addresses `(10.0.0.1, 10.0.0.2)` even before the display period begins
     * Input : Query from 10.0.0.3 to get all banners at at "2018-05-01t01:00:00.000z"
     * Output : "2018-05-01t01:00:00.000z" doesn't fit in any banner display period but there is 1 banner which is displayed to 10.0.0.3 
     * even before display period starts.
     * All banners starts after from 2018-06-01 only but banner C will be displayed to 10.0.0.3 before display starts
     */
    private static boolean test2(PromotionBanner promotionbanner)
	    throws UnknownHostException {
	// Query from 10.0.0.3 to get valid banners at "2018-05-01t01:00:00.000z"
	List<Banner> result = promotionbanner.getBanners(
		InetAddress.getByName("10.0.0.3"), DateUtils.getDateInTimezone(
			"2018-05-01t01:00:00.000z", ZoneId.of("UTC")));
	
	List<InetAddress> ipaddresslist = new ArrayList<>();
	ipaddresslist.add(InetAddress.getByName("10.0.0.3"));

	List<Banner> expected = new ArrayList<>();
	expected.add(new Banner("3", "C",
		DateUtils.getDateInTimezone("2018-06-10t01:00:00.000z",
			ZoneId.of("UTC")),
		DateUtils.getDateInTimezone("2018-06-10t23:59:00.000z",
			ZoneId.of("UTC")),
		ipaddresslist));

	if (!expected.equals(result)) {
	    System.out.println("Test1 : Failed");
	    System.out.println("Expected output :");
	    expected.stream().forEach(i -> System.out.println(i.toString()));
	    System.out.println("Result output :");
	    result.stream().forEach(i -> System.out.println(i.toString()));
	    System.out.println("##############################");
	    return false;
	}
	return true;
    }

}
