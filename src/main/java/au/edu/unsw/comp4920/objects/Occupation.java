package au.edu.unsw.comp4920.objects;

public class Occupation {
	private int occupationID;
	private String name;
	

	public Occupation() {
		
	}

	public Occupation(int occupationID, String name) {
		super();
		this.occupationID = occupationID;
		this.name = name;
	}

	public int getOccupationID() {
		return occupationID;
	}

	public void setOccupationID(int occupationID) {
		this.occupationID = occupationID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Occupation [occupationID=" + occupationID + ", name=" + name + "]";
	}
}
