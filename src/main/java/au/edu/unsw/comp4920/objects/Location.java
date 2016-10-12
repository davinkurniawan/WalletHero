package au.edu.unsw.comp4920.objects;

public class Location {
	String address;
	String locality;
	String region;
	String postal_code;
	String country;
	Double latitude;
	Double longitude;
	
	public Location() {
		super();
	}
	
	public Location(String address, String locality, String region, String postal_code, String country, Double latitude,
			Double longitude) {
		super();
		this.address = address;
		this.locality = locality;
		this.region = region;
		this.postal_code = postal_code;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
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
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Location [address=" + address + ", locality=" + locality + ", region=" + region + ", postal_code="
				+ postal_code + ", country=" + country + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
}
