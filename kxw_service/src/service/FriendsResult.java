package service;

/**
 * �������ݵ�ʵ��
 * 
 * @author gyz
 * 
 */
public class FriendsResult {
	/**
	 * ���ѵı��
	 */
	private String uid;
	/**
	 * ���ѵ�ͷ����
	 */
	private String avatar;
	/**
	 * ���ѵ�����
	 */
	private String name;
	/**
	 * ���ѵ�����ƴ��
	 */
	private String name_pinyin;
	/**
	 * ��������������ĸ
	 */
	private String name_first;
	/**
	 * ����email
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
