package dto;

import java.io.Serializable;

public class BandInfo implements Serializable {
	private int id;            //バンド情報id
	private String name;       //バンド名
	private int user_id;       //ユーザーID
	
	public BandInfo() {
		super();
		this.id = 0;
		this.name = "";
		this.user_id = 0;
	}
	
	public BandInfo(int id, String name, int user_id) {
		super();
		this.id = id;
		this.name = name;
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

}
