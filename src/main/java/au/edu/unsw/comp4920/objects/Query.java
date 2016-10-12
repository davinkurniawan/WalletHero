package au.edu.unsw.comp4920.objects;

import java.util.List;

public class Query {
	int total;
	int page;
	int per_page;
	Location location;
	int radius;
	boolean online;
	List<String> category_slugs;
	List<String> provider_slugs;
	String updated_after;
	
	public Query() {
		super();
	}
	
	public Query(int total, int page, int per_page, Location location, int radius, boolean online,
			List<String> category_slugs, List<String> provider_slugs, String updated_after) {
		super();
		this.total = total;
		this.page = page;
		this.per_page = per_page;
		this.location = location;
		this.radius = radius;
		this.online = online;
		this.category_slugs = category_slugs;
		this.provider_slugs = provider_slugs;
		this.updated_after = updated_after;
	}

	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPer_page() {
		return per_page;
	}
	
	public void setPer_page(int per_page) {
		this.per_page = per_page;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public boolean isOnline() {
		return online;
	}
	
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public List<String> getCategory_slugs() {
		return category_slugs;
	}
	
	public void setCategory_slugs(List<String> category_slugs) {
		this.category_slugs = category_slugs;
	}
	
	public List<String> getProvider_slugs() {
		return provider_slugs;
	}
	
	public void setProvider_slugs(List<String> provider_slugs) {
		this.provider_slugs = provider_slugs;
	}
	
	public String getUpdated_after() {
		return updated_after;
	}
	
	public void setUpdated_after(String updated_after) {
		this.updated_after = updated_after;
	}

	@Override
	public String toString() {
		return "Query [total=" + total + ", page=" + page + ", per_page=" + per_page + ", location=" + location
				+ ", radius=" + radius + ", online=" + online + ", category_slugs=" + category_slugs
				+ ", provider_slugs=" + provider_slugs + ", updated_after=" + updated_after + "]";
	}
}
