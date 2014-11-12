package service.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserInfoResult;

public class UserInfoServlet extends CommonServlet
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
			if("".equals(uid) || uid == null){
				sql = "SELECT u.*, "+
						"(select count(*) from t_kx_photos p where p.user_id = u.id) as photo_count,"+
						"(select count(*) from t_kx_diary d where d.email = u.email) as diary_count,"+
						"(select count(*) from t_kx_friends f where f.my_email = u.email) as friend_count," +
						"(select count(*) from t_kx_visitors v where v.visited_uid = u.id) as visitor_count"+
						"	FROM kxw.t_kx_users u"+
						"	where u.email = ? ";
				preparedStatement = mConnection
						.prepareStatement(sql);
				preparedStatement.setString(1, mUsername);
			}else{
				sql = "SELECT u.*, "+
						"(select count(*) from t_kx_photos p where p.user_id = u.id) as photo_count,"+
						"(select count(*) from t_kx_diary d where d.email = u.email) as diary_count,"+
						"(select count(*) from t_kx_friends f where f.my_email = u.email) as friend_count,"+
						"(select count(*) from t_kx_visitors v where v.visited_uid = u.id) as visitor_count"+
						"	FROM kxw.t_kx_users u"+
						"	where u.id = ? ";
				preparedStatement = mConnection
						.prepareStatement(sql);
				preparedStatement.setString(1, uid);
			}
			
			ResultSet rs = preparedStatement.executeQuery();
			UserInfoResult user = new UserInfoResult();
			while (rs.next())
			{
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setDiary_count(rs.getInt("diary_count"));
				user.setFriend_count(rs.getInt("friend_count"));
				user.setPhoto_count(rs.getInt("photo_count"));
				user.setVisitor_count(rs.getInt("visitor_count"));
				user.setGender(rs.getInt("sex_id"));
				user.setAvatar(rs.getString("avatar"));
				user.setConstellation(rs.getString("constellation"));
				user.setSignature(rs.getString("signature"));
				user.setWallpager(-1);
				user.setDate(rs.getString("birthday"));
			}

//			sendObject(albums, response);
			sendJSON(user, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
