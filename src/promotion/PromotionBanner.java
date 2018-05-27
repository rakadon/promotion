package promotion;

import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.util.List;

import pojo.Banner;

public interface PromotionBanner {

    /**
     * Given an sourceIpAddress and ZoneDateTime display all valid promotion
     * banners.
     * 
     * @param ipAddress
     * @param zoneId
     * @return
     */
    public List<Banner> getBanners(InetAddress ipAddress,
	    ZonedDateTime zonedDateTime);
}
