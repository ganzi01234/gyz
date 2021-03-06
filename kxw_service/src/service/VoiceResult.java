package service;

/**
 * 通话数据的实体
 * 
 * @author gyz
 * 
 */
public class VoiceResult {
	/**
	 * 通话的编号
	 */
	private String vid;
	/**
	 * 通话的名称
	 */
	private String name;
	/**
	 * 通话的url
	 */
	private String url;
	/**
	 * 通话的时间
	 */
	private String time;
	/**
	 * 通话的信息
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
