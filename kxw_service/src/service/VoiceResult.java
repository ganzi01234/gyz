package service;

/**
 * ͨ�����ݵ�ʵ��
 * 
 * @author gyz
 * 
 */
public class VoiceResult {
	/**
	 * ͨ���ı��
	 */
	private String vid;
	/**
	 * ͨ��������
	 */
	private String name;
	/**
	 * ͨ����url
	 */
	private String url;
	/**
	 * ͨ����ʱ��
	 */
	private String time;
	/**
	 * ͨ������Ϣ
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
