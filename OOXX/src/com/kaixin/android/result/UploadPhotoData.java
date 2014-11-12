package com.kaixin.android.result;

import java.io.Serializable;

public class UploadPhotoData implements Serializable
{
    private String username;
    private String passwordMD5;
	private int userId;
    private int albumId;
    private byte[] photoData;
    
    private int messageId;
    
    
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPasswordMD5()
	{
		return passwordMD5;
	}
	public void setPasswordMD5(String passwordMD5)
	{
		this.passwordMD5 = passwordMD5;
	}
	public int getUserId()
	{
		return userId;
	}
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	public int getAlbumId()
	{
		return albumId;
	}
	public void setAlbumId(int albumId)
	{
		this.albumId = albumId;
	}
	public byte[] getPhotoData()
	{
		return photoData;
	}
	public void setPhotoData(byte[] photoData)
	{
		this.photoData = photoData;
	}
    
    
}
