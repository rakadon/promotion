package main.java.pojo;

/**
 * List of resultcode from promotion banner feature
 * 
 * @author rajuu
 *
 */
public enum ResultCode {
    FORMAT_ERROR("format of data for banners inside json file is invalid"),
    FILE_NOT_FOUND("json data file for banners not found"),
    FILE_READ_ERROR("Cannot read or open the json data file"),
    INVALID_IPADDRESS("Input IpAdress is invalid"),
    INVALID_DATETIME("Input DateTime is invalid"),
    INVALID_ZONEID("Input ZoneId is invalid"),
    SUCCESS("success");

    private String url;

    ResultCode(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }
}
