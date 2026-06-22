package dto;

import java.io.Serializable;

public class LoginUser implements Serializable {
	private String user_id; // ログイン時のID
	private int type; // タイプの保持

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getId() {
		return user_id;
	}

	public void setUserId(String user_id) {
		this.user_id = user_id;
	}

	public LoginUser() {
		this("", 0);
	}

	public LoginUser(String user_id) {
		this(user_id, 0);
	}
	
	public LoginUser(String user_id, int type) {
		this.user_id = user_id;
		this.type = type;
	}
}