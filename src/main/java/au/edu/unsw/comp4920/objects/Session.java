package au.edu.unsw.comp4920.objects;

public class Session {
	private String sessionID;
	private int userID;
	private String lastAccess;
	
	public Session() {
		
	}

	public Session(String sessionID, int userID, String lastAccess) {
		super();
		this.sessionID = sessionID;
		this.userID = userID;
		this.lastAccess = lastAccess;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(String lastAccess) {
		this.lastAccess = lastAccess;
	}

	@Override
	public String toString() {
		return "Session [sessionId=" + sessionID + ", userId=" + userID + ", lastAccess=" + lastAccess + "]";
	}
}
