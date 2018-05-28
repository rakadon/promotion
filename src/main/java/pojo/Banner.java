package main.java.pojo;

import java.time.ZonedDateTime;
import java.util.List;

/*
 * Pojo class which stores the details of a promotion banner.
 */
public class Banner {
	public Banner() {}

	public Banner(String bannerId, String bannerName, ZonedDateTime startDateTime, 
			ZonedDateTime endDateTime,List<String> permittedIP) {
		super();
		this.bannerId = bannerId;
		this.bannerName = bannerName;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.permittedIP = permittedIP;
	}

	private String bannerId;
	private String bannerName;
	private ZonedDateTime startDateTime;
	private ZonedDateTime endDateTime;
	private List<String> permittedIP;

	public String getBannerId() {
		return bannerId;
	}

	public void setBannerId(String bannerId) {
		this.bannerId = bannerId;
	}

	public String getBannerName() {
		return bannerName;
	}

	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}

	public ZonedDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(ZonedDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public ZonedDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(ZonedDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public List<String> getPermittedIP() {
		return permittedIP;
	}

	public void setPermittedIP(List<String> permittedIP) {
		this.permittedIP = permittedIP;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bannerId == null) ? 0 : bannerId.hashCode());
		result = prime * result + ((bannerName == null) ? 0 : bannerName.hashCode());
		result = prime * result + ((endDateTime == null) ? 0 : endDateTime.hashCode());
		result = prime * result + ((permittedIP == null) ? 0 : permittedIP.hashCode());
		result = prime * result + ((startDateTime == null) ? 0 : startDateTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Banner other = (Banner) obj;
		if (bannerId == null) {
			if (other.bannerId != null)
				return false;
		} else if (!bannerId.equals(other.bannerId))
			return false;
		if (bannerName == null) {
			if (other.bannerName != null)
				return false;
		} else if (!bannerName.equals(other.bannerName))
			return false;
		if (endDateTime == null) {
			if (other.endDateTime != null)
				return false;
		} else if (!endDateTime.equals(other.endDateTime))
			return false;
		if (permittedIP == null) {
			if (other.permittedIP != null)
				return false;
		} else if (!permittedIP.equals(other.permittedIP))
			return false;
		if (startDateTime == null) {
			if (other.startDateTime != null)
				return false;
		} else if (!startDateTime.equals(other.startDateTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Banner [bannerId=" + bannerId + ", bannerName=" + bannerName + ", startDateTime=" + startDateTime
				+ ", endDateTime=" + endDateTime + ", permittedIP=" + permittedIP + "]";
	}

}
