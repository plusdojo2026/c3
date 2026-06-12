package dto;

import java.time.LocalDateTime;

public class LiveInfo {
	private int id;                  //ライブ情報ID
	private String name;             //ライブ名
	private LocalDateTime begin_date;    //開始日時
	private LocalDateTime end_date;	     //終了日時
	private int user_id;  	         //ユーザーID
	
	public LiveInfo() {
		super();
		this.id = 0;
		this.name = "";
		this.begin_date = null;
		this.end_date = null;
		this.user_id = 0;
	}
	
	public LiveInfo(int id, String name, LocalDateTime begin_date, LocalDateTime end_date, int user_id) {
		super();
		this.id = id;
		this.name = name;
		this.begin_date = begin_date;
		this.end_date = end_date;
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

	public LocalDateTime getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(LocalDateTime begin_date) {
		this.begin_date = begin_date;
	}

	public LocalDateTime getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDateTime end_date) {
		this.end_date = end_date;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
}

