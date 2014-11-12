package service;

import java.io.Serializable;

public class UploadPhotoData implements Serializable
{
    private String username;
    private String passwordMD5;
	private int userId;
    private int albumId;
    private byte[] photoData;
    private String messageTime;
    
    
	public String getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(String messageTime) {
		this.messageTime = messageTime;
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
