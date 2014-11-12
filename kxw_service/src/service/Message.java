package service;

import java.util.List;

/**
 * @author gyz
 *
 */
public class Message {
	/**
	 * �û�ID
	 *
	 */
	private String uid;
	/**
	 * �û��ĵ�½�˺�
	 */
	private String email;
	/**
	 * �û��ǳ�
	 *
	 */
	private String name;
	/**
	 * �û�ͷ��
	 *
	 */
	private String avatar;
	/**
	 * ��Ϣ����
	 * photo �� viewed
	 */
	private String type;
	/**
	 * ��Ϣʱ��
	 *
	 */
	private String time;
	/**
	 * ��Ϣ����
	 *
	 */
	private String title;
	/**
	 * ��Ϣ��Դ
	 *
	 */
	private String from;
	/**
	 * ��������
	 *
	 */
	private int comment_count;
	/**
	 * ������
	 *
	 */
	private int like_count;
	/**
	 * ͼƬ��ַ
	 *
	 */
	private List<String> photo;
	/**
	 * ��ϢID
	 *
	 */
	private String messageid;
	/**
	 * ���ID
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
