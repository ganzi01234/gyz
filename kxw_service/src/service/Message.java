package service;

import java.util.List;

/**
 * @author gyz
 *
 */
public class Message {
	/**
	 * 用户ID
	 *
	 */
	private String uid;
	/**
	 * 用户的登陆账号
	 */
	private String email;
	/**
	 * 用户昵称
	 *
	 */
	private String name;
	/**
	 * 用户头像
	 *
	 */
	private String avatar;
	/**
	 * 消息类型
	 * photo 和 viewed
	 */
	private String type;
	/**
	 * 消息时间
	 *
	 */
	private String time;
	/**
	 * 消息标题
	 *
	 */
	private String title;
	/**
	 * 消息来源
	 *
	 */
	private String from;
	/**
	 * 评论总数
	 *
	 */
	private int comment_count;
	/**
	 * 点赞数
	 *
	 */
	private int like_count;
	/**
	 * 图片地址
	 *
	 */
	private List<String> photo;
	/**
	 * 消息ID
	 *
	 */
	private String messageid;
	/**
	 * 相册ID
	 *
	 */
	private int albumid;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAlbumid() {
		return albumid;
	}
	public void setAlbumid(int albumid) {
		this.albumid = albumid;
	}
	public String getMessageid() {
		return messageid;
	}
	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public List<String> getPhoto() {
		return photo;
	}
	public void setPhoto(List<String> photo) {
		this.photo = photo;
	}
	
	
	
}
