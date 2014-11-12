package com.kaixin.android.result;

import java.io.Serializable;
import java.util.List;

/**
 * 首页内容的实体类
 * 
 * @author gyz
 * 
 */
public class HomeResult implements Serializable{
	/**
	 * 用户的ID
	 */
	private int uid;
	/**
	 * 用户的登陆账号
	 */
	private String email;
	/**
	 * 用户的姓名
	 */
	private String name;
	/**
	 * 用户的头像
	 */
	private String avatar;
	/**
	 * 内容的类型
	 */
	private String type;
	/**
	 * 内容的时间
	 */
	private String time;
	/**
	 * 内容的标题
	 */
	private String title;
	/**
	 * 来自什么客户端
	 */
	private String from;
	/**
	 * 评论数量
	 */
	private int comment_count;
	/**
	 * 赞数量
	 */
	private int like_count;
	/**
	 * 内容的图片
	 */
	private List<String> photo;
	/**
	 * 消息ID
	 *
	 */
	private int messageid;
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
	public int getMessageid() {
		return messageid;
	}
	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
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
