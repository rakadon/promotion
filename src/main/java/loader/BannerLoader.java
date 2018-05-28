package main.java.loader;

import java.util.List;

import main.java.pojo.Banner;
import main.java.pojo.PromotionOutput;
import main.java.pojo.ResultCode;


/**
 * @author raju
 *
 */
public interface BannerLoader {

	/**
	 * Read the banners from json file or DB by implementing this function
	 * 
	 * @return pair which has resultcode and result
	 */
	public PromotionOutput<ResultCode,List<Banner>>  getBanners();

}
