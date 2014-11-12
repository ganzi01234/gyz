package service;

import java.io.Serializable;
import java.util.List;

/**
 * ������ݵ�ʵ��
 * 
 * @author gyz
 * 
 */
public class PhotoResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4130287967283631713L;
	/**
	 * ���ı��
	 */
	private String pid;
	/**
	 * ���ķ���ͼƬ�ı��
	 */
	private int image;
	/**
	 * ���ı���
	 */
	private String title;
	/**
	 * �������Ƭ������
	 */
	private int count;
	/**
	 * ��ᴴ��ʱ��
	 */
	private String time;
	/**
	 * ��������(0-ͷ�����,1-��Ƭ���)
	 */
	private int type;
	/**
	 * �������Ƭ�ľ�������
	 */
	private List<PhotoDetailResult> images;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<PhotoDetailResult> getImages() {
		return images;
	}

	public void setImages(List<PhotoDetailResult> images) {
		this.images = images;
	}

	public int describeContents() {
		return 0;
	}

	/*public void writeToParcel(Serializable dest, int flags) {
		dest.writeString(pid);
		dest.writeString(title);
		dest.writeString(time);
		dest.writeInt(image);
		dest.writeInt(count);
		dest.writeInt(type);
		dest.writeList(images);
	}

	public static final Creator<PhotoResult> CREATOR = new Creator<PhotoResult>() {

		public PhotoResult[] newArray(int size) {
			return new PhotoResult[size];
		}

		@SuppressWarnings("unchecked")
		public PhotoResult createFromParcel(Parcel source) {
			PhotoResult result = new PhotoResult();
			result.setPid(source.readString());
			result.setTitle(source.readString());
			result.setTime(source.readString());
			result.setImage(source.readInt());
			result.setCount(source.readInt());
			result.setType(source.readInt());
			result.setImages(source.readArrayList(PhotoResult.class
					.getClassLoader()));
			return result;
		}
	};*/
}
