package io.agileIntelligence.ppmtool2.exception;

// Note - The reason that we give error as "Invalid username and password" rather than "Invalid username" OR "Invalid Password" because we don't want to give hints to malicious people as to what exactly is invalid
public class InvalidLoginResponse {//It's the JSON object that we want to return

	private String username;
	private String password;
	
	public InvalidLoginResponse() {
		this.username = "Invalid Username";
		this.password = "Invalid Password";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
