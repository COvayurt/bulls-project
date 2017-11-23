package model;

import java.util.List;


public class User {

	private String username;
	private String password;
	private String email;
	private String nameSurname;
	private String accessToken;
	private Long tokenExpirationTime;
	private List<BullsTicker> userFollowTickers;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNameSurname() {
		return nameSurname;
	}

	public void setNameSurname(String nameSurname) {
		this.nameSurname = nameSurname;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getTokenExpirationTime() {
		return tokenExpirationTime;
	}

	public void setTokenExpirationTime(Long tokenExpirationTime) {
		this.tokenExpirationTime = tokenExpirationTime;
	}

	public List<BullsTicker> getUserFollowTickers() {
		return userFollowTickers;
	}

	public void setUserFollowTickers(List<BullsTicker> userFollowTickers) {
		this.userFollowTickers = userFollowTickers;
	}
}
