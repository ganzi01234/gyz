package service;

import java.util.List;
import java.util.Map;

/**
 * ��������Ƭ��������ʵ����
 * 
 * @author gyz
 * 
 */
public class NearbyPhotoDetail {
	/**
	 * ��Ƭ����
	 */
	private String image;
	/**
	 * ��Ƭ������ID
	 */
	private String owners_uid;
	/**
	 * ��Ƭ����������
	 */
	private String owners_name;
	/**
	 * ��Ƭ���е�ͷ��
	 */
	private String owners_avatar;
	/**
	 * ��Ƭ�ϴ�ʱ��
	 */
	private String time;
	/**
	 * ��Ƭ����
	 */
	private String description;
	/**
	 * ��Ƭ��������
	 */
	private int comment_count;
	/**
	 * ��Ƭ������
	 */
	private int like_count;
	/**
	 * ��Ƭ��������
	 */
	private List<Map<String, Object>> comments;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getOwners_uid() {
		return owners_uid;
	}

	public void setOwners_uid(String owners_uid) {
		this.owners_uid = owners_uid;
	}

	public String getOwners_name() {
		return owners_name;
	}

	public void setOwners_name(String owners_name) {
		this.owners_name = owners_name;
	}

	public String getOwners_avatar() {
		return owners_avatar;
	}

	public void setOwners_avatar(String owners_avatar) {
		this.owners_avatar = owners_avatar;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<Map<String, Object>> getComments() {
		return comments;
	}

	public void setComments(List<Map<String, Object>> comments) {
		this.comments = comments;
	}
}
