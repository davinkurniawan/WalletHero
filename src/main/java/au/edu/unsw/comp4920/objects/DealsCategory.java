package au.edu.unsw.comp4920.objects;

public class DealsCategory {
	String slug;
	String name;
	String parent_slug;
	
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent_slug() {
		return parent_slug;
	}
	public void setParent_slug(String parent_slug) {
		this.parent_slug = parent_slug;
	}
}
