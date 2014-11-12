package service;

import java.io.Serializable;

public class Gift implements Serializable
{
	private int giftCode;
	private String postscript;
	public int getGiftCode()
	{
		return giftCode;
	}
	public void setGiftCode(int giftCode)
	{
		this.giftCode = giftCode;
	}
	public String getPostscript()
	{
		return postscript;
	}
	public void setPostscript(String postscript)
	{
		this.postscript = postscript;
	}
	
}
