package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginService extends Database
{
	public User login(String username, String password)
	{
		String sql = "select * from t_kx_users where email=? and password=?";
		User user = null;
		try
		{
			PreparedStatement pstmt = mConnection.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();		
			if (rs.next())
			{
				user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
			}
		}
		catch (Exception e)  
		{
			System.out.println(e.getMessage());
		}

		return user;
	}
}
