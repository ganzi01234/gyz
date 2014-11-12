package com.kaixin.android.result;


public class UserResult
{
	private int id;
	private String email;
	private String name;
	private String passwordMD5;
	private boolean isFriend;
	private String avatar;
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean isFriend() {
		return isFriend;
	}

	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPasswordMD5()
	{
		return passwordMD5;
	}

	public void setPasswordMD5(String passwordMD5)
	{
		this.passwordMD5 = passwordMD5;
	}

}
