package au.edu.unsw.comp4920.common;

import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.comp4920.objects.*;

public interface CommonDAO {
	public User getUserDetails(String username);
	public User getUserDetails(int uid); 
	
	//TODO
}
