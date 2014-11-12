package service;

public class Update {
	private String code;
	private String info;
	private String name;
	private boolean type;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isType() {
		return type;
	}
	public void setType(boolean type) {
		this.type = type;
	}
	public Update(String code, String info, String name, boolean type) {
		super();
		this.code = code;
		this.info = info;
		this.name = name;
		this.type = type;
	}
	
	
}
