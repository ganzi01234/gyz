package com.kaixin.android.result;

public class ConversationResult {
	private String id;
	private String name;
	private String email;
	private String time;
	private String avatar;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public ConversationResult() {
		super();
	}
	public ConversationResult(String name, String email, String avatar) {
		super();
		this.name = name;
		this.email = email;
		this.avatar = avatar;
	}
	
	
	
}
