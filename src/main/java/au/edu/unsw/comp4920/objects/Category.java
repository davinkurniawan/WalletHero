package au.edu.unsw.comp4920.objects;

/**
 * 
 * This object is highly complicated.
 *
 */

public class Category {
	private int categoryID;
	private String category;
	
	public Category() {
		super();
	}
	
	public Category(String category, int categoryID) {
		super();
		this.categoryID = categoryID;
		this.category = category;
	}
	
	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Category [categoryID=" + categoryID + ", category=" + category + "]";
	}
}
