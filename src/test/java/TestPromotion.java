package test.java;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import main.java.pojo.Banner;
import main.java.pojo.PromotionOutput;
import main.java.pojo.ResultCode;
import main.java.promotion.PromotionBanner;
import main.java.promotion.PromotionBannerImpl;
import main.java.util.DateUtil;

public class TestPromotion {

	public static void main(String[] args) {
		PromotionBanner promotionBanner = new PromotionBannerImpl();

		if (duringDisplayPeriod(promotionBanner)) {
			System.out.println("DuringDisplayPeriod test : Passed");
			System.out.println("##############################");
		}
		if (afterDisplayPeriod(promotionBanner)) {
			System.out.println("AfterDisplayPeriod test : Passed");
			System.out.println("##############################");
		}
		if (beforeDisplayPeriod(promotionBanner)) {
			System.out.println("BeforeDisplayPeriod test : Passed");
			System.out.println("##############################");
		}
		if (invalidInputDateTime(promotionBanner)) {
			System.out.println("InvalidInputDateTime test : Passed");
			System.out.println("##############################");
		}
		if (invalidIpAdress(promotionBanner)) {
			System.out.println("InvalidIpAdress test : Passed");
			System.out.println("##############################");
		}
		if (permittedIpAfterDisplayPeriod(promotionBanner)) {
			System.out.println("PermittedIpAfterDisplayPeriod test : Passed");
			System.out.println("##############################");
		}
		if (permittedIpBeforeDisplayPeriod(promotionBanner)) {
			System.out.println("PermittedIpBeforeDisplayPeriod test : Passed");
			System.out.println("##############################");
		}
		if (differentTimeZone1(promotionBanner)) {
			System.out.println("DifferentTimeZone1 test : Passed");
			System.out.println("##############################");
		}
		if (differentTimeZone2(promotionBanner)) {
			System.out.println("DifferentTimeZone2 test : Passed");
			System.out.println("##############################");
		}

	}

	/**
	 * Input : Query from 10.0.0.1 to get valid banners at "2018-06-01T01:00:00" 
	 * Output : There are 2 banner which are active at "2018-06-01T01:00:00"
	 *
	 */
	private static boolean duringDisplayPeriod(PromotionBanner promotionbanner) {
		PromotionOutput<ResultCode,List<Banner>> result = promotionbanner.getBanners("10.0.0.1","2018-06-01T01:00:00","UTC");
		
		List<String> ipaddresslist = new ArrayList<>();
		ipaddresslist.add("10.0.0.1");
		ipaddresslist.add("10.0.0.2");

		List<Banner> expected = new ArrayList<>();
		expected.add(new Banner("1", "A", DateUtil.getDateInTimezone("2018-06-01T01:00:00.000Z", ZoneId.of("UTC")),
				DateUtil.getDateInTimezone("2018-06-01T23:59:00.000Z", ZoneId.of("UTC")), ipaddresslist));
		expected.add(new Banner("2", "B", DateUtil.getDateInTimezone("2018-06-01T01:00:00.000Z", ZoneId.of("UTC")),
				DateUtil.getDateInTimezone("2018-06-01T23:59:00.000Z", ZoneId.of("UTC")), ipaddresslist));

		if (!ResultCode.SUCCESS.equals(result.getResultCode()) || 
				!expected.equals(result.getBanners())) {
			System.out.println("duringDisplayPeriod test : Failed");
			System.out.println("------------------------------");
			System.out.println("Expected output :");
			System.out.println("Resultcode : " + ResultCode.SUCCESS.toString());
			System.out.println("Banners : ");
			expected.stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("------------------------------");
			System.out.println("Result output :");
			System.out.println("Resultcode : " + result.getResultCode().toString());
			System.out.println("Banners : ");
			result.getBanners().stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("##############################");
			return false;
		}
		return true;
	}

	/**
	 * The banner should be displayed to permitted IP addresses `(10.0.0.1, 10.0.0.2)` even before the display period begins 
	 * 
	 * Input : Query from 10.0.0.3 to get all banners at at "2018-05-01T01:00:00" 
	 * Output : "2018-05-01T01:00:00" doesn't fit in any banner display period but there
	 * is 1 banner which is displayed to 10.0.0.3 even before display period starts.
	 * 
	 * All banners starts after from 2018-06-01, but banner C will be displayed
	 * to 10.0.0.3 before display starts
	 */
	private static boolean permittedIpBeforeDisplayPeriod(PromotionBanner promotionbanner) {
		// Query from 10.0.0.3 to get valid banners at "2018-05-01t01:00:00"
		PromotionOutput<ResultCode,List<Banner>> result = promotionbanner.getBanners("10.0.0.3", "2018-05-01T01:00:00","UTC");

		List<String> ipaddresslist = new ArrayList<>();
		ipaddresslist.add("10.0.0.3");

		List<Banner> expected = new ArrayList<>();
		expected.add(new Banner("3", "C", DateUtil.getDateInTimezone("2018-06-10T01:00:00.000Z", ZoneId.of("UTC")),
				DateUtil.getDateInTimezone("2018-06-10T23:59:00.000Z", ZoneId.of("UTC")), ipaddresslist));

		if (!ResultCode.SUCCESS.equals(result.getResultCode()) || 
				!expected.equals(result.getBanners())) {
			System.out.println("PermittedIpBeforeDisplayPeriod test : Failed");
			System.out.println("------------------------------");
			System.out.println("Expected output :");
			System.out.println("Resultcode : " + ResultCode.SUCCESS.toString());
			System.out.println("Banners : ");
			expected.stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("------------------------------");
			System.out.println("Result output :");
			System.out.println("Resultcode : " + result.getResultCode().toString());
			System.out.println("Banners : ");
			result.getBanners().stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("##############################");
			return false;
		}
		return true;
	}

	/**
	 * No banner available before the display period of any banner starts
	 * 
	 * Input : Query from 10.0.0.10 to get all banners at at "2018-05-01T01:00:00" 
	 * Output : All the banners starts from "2018-05-01T01:00:00", therefore no banner available
	 * 
	 */
	private static boolean beforeDisplayPeriod(PromotionBanner promotionbanner) {
		PromotionOutput<ResultCode,List<Banner>> result = promotionbanner.getBanners("10.0.0.10", "2018-05-01T01:00:00","UTC");

		List<Banner> expected = new ArrayList<>();

		if (!ResultCode.SUCCESS.equals(result.getResultCode()) || 
				!expected.equals(result.getBanners())) {
			System.out.println("BeforeDisplayPeriod test : Failed");
			System.out.println("------------------------------");
			System.out.println("Expected output :");
			System.out.println("Resultcode : " + ResultCode.SUCCESS.toString());
			System.out.println("Banners : ");
			expected.stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("------------------------------");
			System.out.println("Result output :");
			System.out.println("Resultcode : " + result.getResultCode().toString());
			System.out.println("Banners : ");
			result.getBanners().stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("##############################");
			return false;
		}
		return true;
	}
	
	/**
	 * No banner available after the display period ends for all banners
	 * 
	 * Input : Query from 10.0.0.10 to get all banners at at "2018-10-01T01:00:00" 
	 * Output : There is no banner whose enddate is after "2018-10-01T01:00:00"
	 * 
	 */
	private static boolean afterDisplayPeriod(PromotionBanner promotionbanner) {
		PromotionOutput<ResultCode,List<Banner>> result = promotionbanner.getBanners("10.0.0.10", "2018-10-01T01:00:00","UTC");

		List<Banner> expected = new ArrayList<>();

		if (!ResultCode.SUCCESS.equals(result.getResultCode()) || 
				!expected.equals(result.getBanners())) {
			System.out.println("AfterDisplayPeriod test : Failed");
			System.out.println("------------------------------");
			System.out.println("Expected output :");
			System.out.println("Resultcode : " + ResultCode.SUCCESS.toString());
			System.out.println("Banners : ");
			expected.stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("------------------------------");
			System.out.println("Result output :");
			System.out.println("Resultcode : " + result.getResultCode().toString());
			System.out.println("Banners : ");
			result.getBanners().stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("##############################");
			return false;
		}
		return true;
	}
	
	/**
	 * No banner available to even permitted Ip's after the display period ends for all banners
	 * 
	 * Input : Query from 10.0.0.1 to get all banners at at "2018-10-01T01:00:00" 
	 * Output : There is no banner whose enddate is after "2018-10-01T01:00:00" even though 10.0.0.1 is permitted ip
	 * 
	 */
	private static boolean permittedIpAfterDisplayPeriod(PromotionBanner promotionbanner) {
		PromotionOutput<ResultCode,List<Banner>> result = promotionbanner.getBanners("10.0.0.1", "2018-10-01T01:00:00", "UTC");

		List<Banner> expected = new ArrayList<>();

		if (!ResultCode.SUCCESS.equals(result.getResultCode()) || 
				!expected.equals(result.getBanners())) {
			System.out.println("PermittedIpAfterDisplayPeriod test : Failed");
			System.out.println("------------------------------");
			System.out.println("Expected output :");
			System.out.println("Resultcode : " + ResultCode.SUCCESS.toString());
			System.out.println("Banners : ");
			expected.stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("------------------------------");
			System.out.println("Result output :");
			System.out.println("Resultcode : " + result.getResultCode().toString());
			System.out.println("Banners : ");
			result.getBanners().stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("##############################");
			return false;
		}
		return true;
	}
	
	/**
	 * Invalid input ip address
	 * 
	 * Input : Query from 10.1.1.1.1 to get all banners at at "2018-06-01T01:00:00" 
	 * Output : invalid ip address
	 * 
	 */
	private static boolean invalidIpAdress(PromotionBanner promotionbanner) {
		PromotionOutput<ResultCode,List<Banner>> result = promotionbanner.getBanners("10.1.1.1.1", "2018-06-01T01:00:00", "UTC");

		if (!ResultCode.INVALID_IPADDRESS.equals(result.getResultCode())) {
			System.out.println("InvalidIpAdress test : Failed");
			System.out.println("------------------------------");
			System.out.println("Expected output :");
			System.out.println("Resultcode : " + ResultCode.INVALID_IPADDRESS.toString());
			System.out.println("------------------------------");
			System.out.println("Result output :");
			System.out.println("Resultcode : " + result.getResultCode().toString());
			System.out.println("##############################");
			return false;
		}
		return true;
	}
	
	/**
	 * Invalid input zonedDateTime
	 * 
	 * Input : Query from 10.1.1.1 to get all banners at at "2018-100-01T01:00:00" 
	 * Output : invalid ip address
	 * 
	 */
	private static boolean invalidInputDateTime(PromotionBanner promotionbanner) {
		PromotionOutput<ResultCode,List<Banner>> result = promotionbanner.getBanners("10.1.1.1", "2018-100-01T01:00:00", "UTC");

		if (!ResultCode.INVALID_DATETIME.equals(result.getResultCode())) {
			System.out.println("InvalidInputZoneDateTime test : Failed");
			System.out.println("------------------------------");
			System.out.println("Expected output :");
			System.out.println("Resultcode : " + ResultCode.INVALID_DATETIME.toString());
			System.out.println("------------------------------");
			System.out.println("Result output :");
			System.out.println("Resultcode : " + result.getResultCode().toString());
			System.out.println("##############################");
			return false;
		}
		return true;
	}
	
	/**
	 * Different timezone test
	 * 
	 * Input : Query from 10.1.1.1 to get all banners at "2018-06-01T16:00:00" Etc/GMT+12
	 * Output : Empty list because Etc/GMT+12 timezone is +12 hours to UTC and there is no banner at "2018-06-01T16:00:00.000Etc/GMT+12"
	 * 
	 */
	private static boolean differentTimeZone1(PromotionBanner promotionbanner) {
		PromotionOutput<ResultCode,List<Banner>> result = promotionbanner.getBanners("10.1.1.1", "2018-06-01T16:00:00","Etc/GMT+12");

		if (!ResultCode.SUCCESS.equals(result.getResultCode()) || !result.getBanners().isEmpty()) {
			System.out.println("DifferentTimeZone1 test : Failed");
			System.out.println("------------------------------");
			System.out.println("Expected output :");
			System.out.println("Resultcode : " + ResultCode.SUCCESS.toString());
			System.out.println("Banners : Empty list");
			System.out.println("------------------------------");
			System.out.println("Result output :");
			System.out.println("Resultcode : " + result.getResultCode().toString());
			System.out.println("Banners : ");
			result.getBanners().stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("##############################");
			return false;
		}
		return true;
	}
	
	/**
	 * Different timezone test
	 * 
	 * Input : Query from 10.1.1.1 to get all banners at at "2018-01-11T02:00:00.000NZ" 
	 * Output : Banner C, even though banner C starts at "2018-01-10T20:00:00.00" its visible at "2018-01-11T02:00:00.000NZ"
	 * because NZ is -12 hours to UTC timezone.
	 * 
	 */
	private static boolean differentTimeZone2(PromotionBanner promotionbanner) {
		PromotionOutput<ResultCode,List<Banner>> result = promotionbanner.getBanners("10.1.1.1", "2018-06-11T02:00:00", "NZ");
		
		List<String> ipaddresslist = new ArrayList<>();
		ipaddresslist.add("10.0.0.3");

		List<Banner> expected = new ArrayList<>();
		expected.add(new Banner("3", "C", DateUtil.getDateInTimezone("2018-06-10T01:00:00.000Z", ZoneId.of("UTC")),
				DateUtil.getDateInTimezone("2018-06-10T23:59:00.000Z", ZoneId.of("UTC")), ipaddresslist));

		if (!ResultCode.SUCCESS.equals(result.getResultCode()) ||
				!expected.equals(result.getBanners())) {
			System.out.println("DifferentTimeZone2 test : Failed");
			System.out.println("------------------------------");
			System.out.println("Expected output :");
			System.out.println("Resultcode : " + ResultCode.SUCCESS.toString());
			System.out.println("Banners :");
			expected.stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("------------------------------");
			System.out.println("Result output :");
			System.out.println("Resultcode : " + result.getResultCode().toString());
			System.out.println("Banners : ");
			result.getBanners().stream().forEach(i -> System.out.println(i.toString()));
			System.out.println("##############################");
			return false;
		}
		return true;
	}
}
