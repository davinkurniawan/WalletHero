package au.edu.unsw.comp4920.objects;

public class Deal {
	
	private String id;
	private String title;
	private String short_title;
	private String description;
	private String fine_print;
	private int number_sold;
	private String url;
	private String untracked_url;
	private Double price;
	private Double discount_amount;
	private Double discount_percentage;
	private Double value;
	private Double commission;
	private String provider_name;
	private String provider_slug;
	private String category_name;
	private String category_slug;
	private String image_url;
	private boolean online;
	private String expires_at;
	private String created_at;
	private String updated_at;
	private Merchant merchant;
	
	public Deal() {
		super();
	}
	
	public Deal(String id, String title, String short_title, String description, String fine_print, int number_sold,
			String url, String untracked_url, Double price, Double discount_amount, Double discount_percentage,
			Double value, Double commission, String provider_name, String provider_slug, String category_name,
			String category_slug, String image_url, boolean online, String expires_at, String created_at,
			String updated_at, Merchant merchant) {
		super();
		this.id = id;
		this.title = title;
		this.short_title = short_title;
		this.description = description;
		this.fine_print = fine_print;
		this.number_sold = number_sold;
		this.url = url;
		this.untracked_url = untracked_url;
		this.price = price;
		this.discount_amount = discount_amount;
		this.discount_percentage = discount_percentage;
		this.value = value;
		this.commission = commission;
		this.provider_name = provider_name;
		this.provider_slug = provider_slug;
		this.category_name = category_name;
		this.category_slug = category_slug;
		this.image_url = image_url;
		this.online = online;
		this.expires_at = expires_at;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.merchant = merchant;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShort_title() {
		return short_title;
	}

	public void setShort_title(String short_title) {
		this.short_title = short_title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFine_print() {
		return fine_print;
	}

	public void setFine_print(String fine_print) {
		this.fine_print = fine_print;
	}

	public int getNumber_sold() {
		return number_sold;
	}

	public void setNumber_sold(int number_sold) {
		this.number_sold = number_sold;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUntracked_url() {
		return untracked_url;
	}

	public void setUntracked_url(String untracked_url) {
		this.untracked_url = untracked_url;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(Double discount_amount) {
		this.discount_amount = discount_amount;
	}

	public Double getDiscount_percentage() {
		return discount_percentage;
	}

	public void setDiscount_percentage(Double discount_percentage) {
		this.discount_percentage = discount_percentage;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getProvider_name() {
		return provider_name;
	}

	public void setProvider_name(String provider_name) {
		this.provider_name = provider_name;
	}

	public String getProvider_slug() {
		return provider_slug;
	}

	public void setProvider_slug(String provider_slug) {
		this.provider_slug = provider_slug;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getCategory_slug() {
		return category_slug;
	}

	public void setCategory_slug(String category_slug) {
		this.category_slug = category_slug;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getExpires_at() {
		return expires_at;
	}

	public void setExpires_at(String expires_at) {
		this.expires_at = expires_at;
	}
	
	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	@Override
	public String toString() {
		return "Deal [id=" + id + ", title=" + title + ", short_title=" + short_title + ", description=" + description
				+ ", fine_print=" + fine_print + ", number_sold=" + number_sold + ", url=" + url + ", untracked_url="
				+ untracked_url + ", price=" + price + ", discount_amount=" + discount_amount + ", discount_percentage="
				+ discount_percentage + ", value=" + value + ", commission=" + commission + ", provider_name="
				+ provider_name + ", provider_slug=" + provider_slug + ", category_name=" + category_name
				+ ", category_slug=" + category_slug + ", image_url=" + image_url + ", online=" + online
				+ ", expires_at=" + expires_at + ", created_at=" + created_at + ", updated_at=" + updated_at
				+ ", merchant=" + merchant + "]";
	}	
}
