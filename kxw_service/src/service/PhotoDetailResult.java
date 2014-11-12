package service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ��Ƭ�������ݵ�ʵ��
 * 
 * @author gyz
 * 
 */
public class PhotoDetailResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2864567249588580933L;
	/**
	 * ��Ƭ�ı��
	 */
	private int id;
	/**
	 * ���ı��
	 */
	private int album_id;
	/**
	 * ��Ƭ���ϴ�ʱ��
	 */
	private String time;
	/**
	 * ��Ƭ������
	 */
	private String description;
	/**
	 * ��Ƭ����������
	 */
	private int comment_count;
	/**
	 * ��Ƭ������
	 */
	private String photo_filename;
	/**
	 * ��Ƭ��������
	 */
	private int like_count;
	/**
	 * ��Ƭ������
	 */
	private List<Map<String, Object>> comments;
	
	

	public int getAlbum_id() {
		return album_id;
	}

	public void setAlbum_id(int album_id) {
		this.album_id = album_id;
	}

	public String getPhoto_filename() {
		return photo_filename;
	}

	public void setPhoto_filename(String photo_filename) {
		this.photo_filename = photo_filename;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int describeContents() {
		return 0;
	}

	/*public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(image);
		dest.writeInt(comment_count);
		dest.writeInt(like_count);
		dest.writeString(time);
		dest.writeString(description);
		dest.writeList(comments);
	}

	public static final Parcelable.Creator<PhotoDetailResult> CREATOR = new Parcelable.Creator<PhotoDetailResult>() {

		@SuppressWarnings("unchecked")
		public PhotoDetailResult createFromParcel(Parcel source) {
			PhotoDetailResult result = new PhotoDetailResult();
			result.setImage(source.readInt());
			result.setComment_count(source.readInt());
			result.setLike_count(source.readInt());
			result.setTime(source.readString());
			result.setDescription(source.readString());
			result.setComments(source.readArrayList(PhotoDetailResult.class
					.getClassLoader()));
			return result;
		}

		public PhotoDetailResult[] newArray(int size) {
			return new PhotoDetailResult[size];
		}
	};*/

}
