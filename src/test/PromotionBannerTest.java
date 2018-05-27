package test;


import static org.junit.Assert.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.java.pojo.Banner;
import main.java.processor.PromotionBanner;
import main.java.processor.PromotionBannerImpl;
import main.java.util.DateUtils;



public class PromotionBannerTest {
	PromotionBanner promotionBanner = new PromotionBannerImpl();
	
	@Test
	public void testPrintMessage() throws DateTimeParseException, UnknownHostException {
		List<InetAddress> ipAddressList = new ArrayList<>();
		ipAddressList.add(InetAddress.getByName("10.0.0.1"));
		ipAddressList.add(InetAddress.getByName("10.0.0.2"));
		
		List<Banner> expected = new ArrayList<>();
		expected.add(new Banner("1", "A"
				, DateUtils.getDateInTimezone("2018-06-01T01:00:00.000Z", ZoneId.of("UTC"))
				, DateUtils.getDateInTimezone("2018-06-01T23:59:00.000Z", ZoneId.of("UTC"))
				, ipAddressList));
		expected.add(new Banner("2", "B"
				, DateUtils.getDateInTimezone("2018-06-01T01:00:00.000Z", ZoneId.of("UTC"))
				, DateUtils.getDateInTimezone("2018-06-01T23:59:00.000Z", ZoneId.of("UTC"))
				, ipAddressList));
		
		List<Banner> result = promotionBanner.getBanners(InetAddress.getByName("10.0.0.1"), 
				DateUtils.getDateInTimezone("2018-06-01T01:00:00.000Z", ZoneId.of("UTC")));
		System.out.println("Expected :");
		expected.stream().forEach(i -> System.out.println(i.toString()));
		System.out.println("Result :");
		result.stream().forEach(i -> System.out.println(i.toString()));
		assertEquals(expected,result);
	}

}
