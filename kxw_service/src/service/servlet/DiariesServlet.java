package service.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Diary;

public class DiariesServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		List<Diary> diaries = new ArrayList<Diary>();
		try
		{
			String uid = request.getParameter("uid");
			String sql = "";
			PreparedStatement preparedStatement = null;
			if("".equals(uid)){
				sql = "select d.*, u.avatar from t_kx_diary d " +
						"left join t_kx_users u on u.email = d.email "+
						"where d.email=? order " +
						"by modify_date desc";
				preparedStatement = mConnection
							.prepareStatement(sql);
				preparedStatement.setString(1, mUsername);
			}else{
				sql = "SELECT d.*,u.id, u.avatar FROM t_kx_users u "+
							"inner join t_kx_diary d on d.email = u.email " + 
							"where u.id = ? order by modify_date desc";
				preparedStatement = mConnection
						.prepareStatement(sql);
				preparedStatement.setString(1, uid);
			}
			

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next())
			{
				Diary diary = new Diary();
				diary.setId(rs.getInt("id"));
				diary.setTitle(rs.getString("title"));
				diary.setFilename(rs.getString("filename"));
				diary.setUsername(rs.getString("username"));
				diary.setTime(rs.getString("modify_date"));
				diary.setContent(rs.getString("content"));
				diary.setComment_count(rs.getInt("comment_count"));
				diary.setLike_count(rs.getInt("like_count"));
				diary.setAvatar(rs.getString("avatar"));
				diaries.add(diary);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		sendJSON(diaries, response);
//		sendObject(diaries, response);
	}

}
