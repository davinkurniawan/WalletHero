package au.edu.unsw.comp4920.objects;

public class Session {
	private String sessionId;
	private int userId;
	private String lastAccess;
	
	public Session() {
		
	}

	public Session(String sessionId, int userId, String lastAccess) {
		super();
		this.sessionId = sessionId;
		this.userId = userId;
		this.lastAccess = lastAccess;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(String lastAccess) {
		this.lastAccess = lastAccess;
	}

	@Override
	public String toString() {
		return "Session [sessionId=" + sessionId + ", userId=" + userId + ", lastAccess=" + lastAccess + "]";
	}
}
