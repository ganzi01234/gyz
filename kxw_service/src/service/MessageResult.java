package service;

/**
 * 短信数据的实体
 * 
 * @author gyz
 * 
 */
public class MessageResult {
	/**
	 * 短信的编号
	 */
	private String vid;
	/**
	 * 短信的类型
	 */
	private String title;
	/**
	 * 短信的内容
	 */
	private String content;
	/**
	 * 短信的时间
	 */
	private String time;
	/**
	 * 短信的信息
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
