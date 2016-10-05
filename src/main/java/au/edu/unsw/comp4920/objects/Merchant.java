package au.edu.unsw.comp4920.objects;

public class Merchant {
	private int merchantID;
	private String name;
	private String address;
	private String locality;
	private String region;
	private String postal_code;
	private String country;
	private String country_code;
	private double latitude;
	private double longitude;
	private String phone_number;
	private String url;
	
	public Merchant() {
		super();
	}
	
	public Merchant(int merchantID, String name, String address, String locality, String region, String postal_code,
			String country, String country_code, double latitude, double longitude, String phone_number, String url) {
		super();
		this.merchantID = merchantID;
		this.name = name;
		this.address = address;
		this.locality = locality;
		this.region = region;
		this.postal_code = postal_code;
		this.country = country;
		this.country_code = country_code;
		this.latitude = latitude;
		this.longitude = longitude;
		this.phone_number = phone_number;
		this.url = url;
	}

	public int getMerchantID() {
		return merchantID;
	}

	public void setMerchantID(int merchantID) {
		this.merchantID = merchantID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Merchant [merchantID=" + merchantID + ", name=" + name + ", address=" + address + ", locality="
				+ locality + ", region=" + region + ", postal_code=" + postal_code + ", country=" + country
				+ ", country_code=" + country_code + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", phone_number=" + phone_number + ", url=" + url + "]";
	}
}
