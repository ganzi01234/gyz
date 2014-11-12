package service;

import java.io.Serializable;

public class Visitor implements Serializable
{
    private int id;
    private String visited_uid;
    private String visitor_name;
    private String time;
    private String visitor_uid;
    private String avatar;
    
    public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVisited_uid() {
		return visited_uid;
	}
	public void setVisited_uid(String visited_uid) {
		this.visited_uid = visited_uid;
	}
	public String getVisitor_name() {
		return visitor_name;
	}
	public void setVisitor_name(String visitor_name) {
		this.visitor_name = visitor_name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getVisitor_uid() {
		return visitor_uid;
	}
	public void setVisitor_uid(String visitor_uid) {
		this.visitor_uid = visitor_uid;
	}
	
    
}
