package service;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database
{
	protected Connection mConnection;

	public Database()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			// 获得Connection对象
			mConnection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3308/kxw?characterEncoding=UTF-8",
					"root", "123456");
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
}
