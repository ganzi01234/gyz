package service;

/**
 * �������ݵ�ʵ��
 * 
 * @author gyz
 * 
 */
public class MessageResult {
	/**
	 * ���ŵı��
	 */
	private String vid;
	/**
	 * ���ŵ�����
	 */
	private String title;
	/**
	 * ���ŵ�����
	 */
	private String content;
	/**
	 * ���ŵ�ʱ��
	 */
	private String time;
	/**
	 * ���ŵ���Ϣ
	 */
	private String info;

	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
}
