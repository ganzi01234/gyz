package service;

public class UserInfoResult {
	private int id;
	private String name;
	private String avatar;
	private int gender;
	private String constellation;
	private String signature;
	private int photo_count;
	private int diary_count;
	private int friend_count;
	private int visitor_count;
	private int wallpager;
	private String date;
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
	public String getConstellation() {
		return constellation;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public int getWallpager() {
		return wallpager;
	}
	public void setWallpager(int wallpager) {
		this.wallpager = wallpager;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getPhoto_count() {
		return photo_count;
	}
	public void setPhoto_count(int photo_count) {
		this.photo_count = photo_count;
	}
	public int getDiary_count() {
		return diary_count;
	}
	public void setDiary_count(int diary_count) {
		this.diary_count = diary_count;
	}
	public int getFriend_count() {
		return friend_count;
	}
	public void setFriend_count(int friend_count) {
		this.friend_count = friend_count;
	}
	public int getVisitor_count() {
		return visitor_count;
	}
	public void setVisitor_count(int visitor_count) {
		this.visitor_count = visitor_count;
	}
	
	
}
