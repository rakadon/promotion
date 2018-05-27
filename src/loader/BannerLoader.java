package loader;

import java.util.List;

import pojo.Banner;

/**
 * @author raju
 *
 */
public interface BannerLoader {

	/**
	 * Read the banners from json file or DB by implementing this function
	 * 
	 * @return
	 */
	public List<Banner> getBanners();

}
