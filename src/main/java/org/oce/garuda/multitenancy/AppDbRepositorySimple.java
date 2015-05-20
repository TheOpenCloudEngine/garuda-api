package org.oce.garuda.multitenancy;



public class AppDbRepositorySimple {

	String userId;
	String password;
	String connectionUrl;
	public AppDbRepositorySimple() {
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConnectionUrl() {
		return connectionUrl;
	}
	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}
	
	

}
