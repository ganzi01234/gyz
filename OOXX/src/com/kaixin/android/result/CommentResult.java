package com.kaixin.android.result;

public class CommentResult {
	private int id;
    private int photo_id;
    private int album_id;
    private int diary_id;
    private String content;
    private String time;
    private String username;
    private String nickname;
    private int has_reply;
    private int is_reply;
    private String reply_user;
    private String avatar;
    
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getReply_user() {
		return reply_user;
	}
	public void setReply_user(String reply_user) {
		this.reply_user = reply_user;
	}
	public int getIs_reply() {
		return is_reply;
	}
	public void setIs_reply(int is_reply) {
		this.is_reply = is_reply;
	}
	public int getHas_reply() {
		return has_reply;
	}
	public void setHas_reply(int has_reply) {
		this.has_reply = has_reply;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getDiary_id() {
		return diary_id;
	}
	public void setDiary_id(int diary_id) {
		this.diary_id = diary_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPhoto_id() {
		return photo_id;
	}
	public void setPhoto_id(int photo_id) {
		this.photo_id = photo_id;
	}
	public int getAlbum_id() {
		return album_id;
	}
	public void setAlbum_id(int album_id) {
		this.album_id = album_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
    
}
