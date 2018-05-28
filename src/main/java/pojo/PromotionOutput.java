package main.java.pojo;

public class PromotionOutput<T, P> {
    private T resultCode;
    private P banners;

    public PromotionOutput( T resultCode, P banners ) {
    	this.resultCode = resultCode;
    	this.banners = banners;
    }

    public PromotionOutput() {
	}

	public T getResultCode() {
		return resultCode;
	}

	public void setResultCode(T resultCode) {
		this.resultCode = resultCode;
	}

	public P getBanners() {
		return banners;
	}

	public void setBanners(P banners) {
		this.banners = banners;
	}

	@Override
	public String toString() {
		return "PromotionOutput [resultCode=" + resultCode + ", banners=" + banners + "]";
	}
    
	

}