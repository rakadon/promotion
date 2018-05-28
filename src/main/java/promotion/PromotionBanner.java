package main.java.promotion;

import java.util.List;

import main.java.pojo.Banner;
import main.java.pojo.PromotionOutput;
import main.java.pojo.ResultCode;


public interface PromotionBanner {

    /**
     * Given an sourceIpAddress and ZoneDateTime display all valid promotion
     * banners.
     * 
     * @param ipAddress 
     * @param dateTime Format = "yyyy-MM-dd'T'HH:mm:ss"
     * @param zoneId valid ZoneId
     * @return
     */
    public PromotionOutput<ResultCode,List<Banner>> getBanners(String ipAddress, String localDateTime, String zoneId);
}
