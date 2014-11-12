package service;

import java.util.List;


/**
 * ��������Ƭ����ʵ����
 * 
 * @author gyz
 * 
 */
public class NearbyPhoto {
	/**
	 * ��ƬID
	 */
	private String pid;
	/**
	 * ��Ƭ����
	 */
	private String image;
	/**
	 * ��Ƭ����
	 */
	private String title;
	/**
	 * ��Ƭ����
	 */
	private int count;
	/**
	 * ��Ƭ�ص�
	 */
	private String location;
	/**
	 * �������
	 */
	private double distance;
	/**
	 * ��Ƭ����
	 */
	private List<NearbyPhotoDetail> images;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int describeContents() {
		return 0;
	}

	public List<NearbyPhotoDetail> getImages() {
		return images;
	}

	public void setImages(List<NearbyPhotoDetail> images) {
		this.images = images;
	}
	
	
}
