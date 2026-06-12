package dto;

public class User {
	private String user_id;
	private String password;
	private int type;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User(String user_id, String password) {
		this.user_id = user_id;
		this.password = password;
	}

	public User() {
		this.user_id = "";
		this.password = "";
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
