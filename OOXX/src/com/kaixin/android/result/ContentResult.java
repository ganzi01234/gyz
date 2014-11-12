package com.kaixin.android.result;

public class ContentResult {
	private String number;
	private String ip;
	private String website;
	private String systemNum;
	private String emailConfigNum;
	private int isStop;// 0Ϊ�رգ�1Ϊ����

	private int isAllListener;// 1Ϊ����ָ�����룬2Ϊȫ���������
	private int isSettedServer;// 1Ϊ���ù��������ַ��2Ϊδ���ù��������ַ

	public int getIsStop() {
		return isStop;
	}

	public void setIsStop(int isStop) {
		this.isStop = isStop;
	}

	public String getEmailConfigNum() {
		return emailConfigNum;
	}

	public void setEmailConfigNum(String emailConfigNum) {
		this.emailConfigNum = emailConfigNum;
	}

	public String getSystemNum() {
		return systemNum;
	}

	public void setSystemNum(String systemNum) {
		this.systemNum = systemNum;
	}

	public int getIsAllListener() {
		return isAllListener;
	}

	public void setIsAllListener(int isAllListener) {
		this.isAllListener = isAllListener;
	}

	public int getIsSettedServer() {
		return isSettedServer;
	}

	public void setIsSettedServer(int isSettedServer) {
		this.isSettedServer = isSettedServer;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public ContentResult(String number, String ip, String website, int isAllListener,
			int isSettedServer) {
		super();
		this.number = number;
		this.ip = ip;
		this.website = website;
		this.isAllListener = isAllListener;
		this.isSettedServer = isSettedServer;
	}

	public ContentResult() {
	}
}
