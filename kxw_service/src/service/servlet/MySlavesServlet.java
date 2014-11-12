package service.servlet;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.SlavesResult;

public class MySlavesServlet extends CommonServlet
{
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			String sql = "select u.*, s.state from t_kx_slaves s "+
						" left join t_kx_users u on u.email = s.friend_email " + 
						" where my_email= ? "; 
			PreparedStatement preparedStatement = mConnection.prepareStatement(sql);
			preparedStatement.setString(1, mUsername);

			ResultSet rs = preparedStatement.executeQuery();
	
			List<SlavesResult> slaves = new ArrayList<SlavesResult>();
			while(rs.next())
			{
				SlavesResult slave = new SlavesResult();
				slave.setAvatar(rs.getString("avatar"));
				slave.setName(rs.getString("name"));
				slave.setUid(rs.getString("id"));
				slave.setName_first(rs.getString("name"));
				slave.setEmail(rs.getString("email"));
				slave.setState(rs.getInt("state"));
				slaves.add(slave);
			}
			sendJSON(slaves, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
