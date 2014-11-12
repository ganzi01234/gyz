package service;

/**
 * 好友数据的实体
 * 
 * @author gyz
 * 
 */
public class FriendsResult {
	/**
	 * 好友的编号
	 */
	private String uid;
	/**
	 * 好友的头像编号
	 */
	private String avatar;
	/**
	 * 好友的姓名
	 */
	private String name;
	/**
	 * 好友的姓名拼音
	 */
	private String name_pinyin;
	/**
	 * 好友姓名的首字母
	 */
	private String name_first;
	/**
	 * 好友email
	 */
	private String email;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_pinyin() {
		return name_pinyin;
	}

	public void setName_pinyin(String name_pinyin) {
		this.name_pinyin = name_pinyin;
	}

	public String getName_first() {
		return name_first;
	}

	public void setName_first(String name_first) {
		this.name_first = name_first;
	}
}
