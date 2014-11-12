package com.kaixin.android.result;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Diary implements Serializable
{
    private int id;
    private String title;
    private String filename;
    private String username;
    private String time;
    private String content;
    private int comment_count;
    private int like_count;
    private String avatar;
    
    /**
	 * 照片的评论
	 */
	private List<Map<String, Object>> comments;
	
	
    
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public List<Map<String, Object>> getComments() {
		return comments;
	}
	public void setComments(List<Map<String, Object>> comments) {
		this.comments = comments;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
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
    
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
    
}
