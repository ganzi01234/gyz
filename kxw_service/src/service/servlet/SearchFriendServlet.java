package service.servlet;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.FriendsResult;
import service.User;

public class SearchFriendServlet extends CommonServlet
{
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			String key = new String(request.getParameter("key").getBytes(
					"iso-8859-1"), "utf-8");
			String sql = "";
			PreparedStatement preparedStatement = null;
			
			sql = "select u.* from t_kx_users u " + 
						"where u.name like ? and u.email not in "+
						"(select friend_email from t_kx_friends f where f.my_email = ?) and u.email <> ?"; 
			preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, "%"+key+"%");
			preparedStatement.setString(2, mUsername);
			preparedStatement.setString(3, mUsername);

			ResultSet rs = preparedStatement.executeQuery();
	
			List<User> users = new ArrayList<User>();
			while(rs.next())
			{
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setId(rs.getInt("id"));
				users.add(user);
			}
			sendJSON(users, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
