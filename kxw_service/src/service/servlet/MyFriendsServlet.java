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

public class MyFriendsServlet extends CommonServlet
{
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			String uid = request.getParameter("uid");
			String sql = "";
			PreparedStatement preparedStatement = null;
			if("".equals(uid)){
				sql = "select u.* from t_kx_friends f "+
						" left join t_kx_users u on u.email = f.friend_email " + 
						" where my_email= ? "; 
				preparedStatement = mConnection
							.prepareStatement(sql);
				preparedStatement.setString(1, mUsername);
			}else{
				sql = "select u.* from t_kx_friends f "+
							" left join t_kx_users u on u.email = f.friend_email "+
							" where f.my_email in (select email from t_kx_users where id = ?) "; 
				preparedStatement = mConnection
						.prepareStatement(sql);
				preparedStatement.setString(1, uid);
			}

			ResultSet rs = preparedStatement.executeQuery();
	
			List<FriendsResult> friends = new ArrayList<FriendsResult>();
			while(rs.next())
			{
				FriendsResult friend = new FriendsResult();
				friend.setAvatar(rs.getString("avatar"));
				friend.setName(rs.getString("name"));
				friend.setUid(rs.getString("id"));
				friend.setName_first(rs.getString("name"));
				friend.setEmail(rs.getString("email"));
				friends.add(friend);
			}
			sendJSON(friends, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
