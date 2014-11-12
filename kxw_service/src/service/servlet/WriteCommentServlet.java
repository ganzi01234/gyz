package service.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WriteCommentServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		int diaryId = Integer.parseInt(request.getParameter("diaryId"));
		int photoId = Integer.parseInt(request.getParameter("photoId"));
		int albumId = Integer.parseInt(request.getParameter("albumId"));
		int is_reply = Integer.parseInt(request.getParameter("is_reply"));
		String reply_user = request.getParameter("reply_user");
		String content = new String(request.getParameter("content").getBytes(
				"iso-8859-1"), "utf-8");
		
		String sql = "insert into t_kx_comments(photo_id, album_id, content, time, username,nickname, diary_id, is_reply, reply_user) values(?,?,?,?,?,?,?,?,?)";
		try
		{
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setInt(1, photoId);
			preparedStatement.setInt(2, albumId);
			preparedStatement.setString(3, content);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			Date date = new Date();
			preparedStatement.setString(4, sdf.format(date));
			preparedStatement.setString(5, mUsername);
			preparedStatement.setString(6, mNickname);
			preparedStatement.setInt(7, diaryId);
			preparedStatement.setInt(8, is_reply);
			preparedStatement.setString(9, reply_user);
			preparedStatement.execute();
			
			if(photoId == 0){
				sql = "update t_kx_diary set comment_count = comment_count + 1 where id = ? ";
				preparedStatement = mConnection
						.prepareStatement(sql);
				preparedStatement.setInt(1, diaryId);
				preparedStatement.execute();
				sql = "SELECT LAST_INSERT_ID()";
				preparedStatement = mConnection
						.prepareStatement(sql);
				preparedStatement.setInt(1, diaryId);
				preparedStatement.execute();
				/*sql = "update t_kx_users u " +
							"left join t_kx_diary d on d.email = u.email " +
							"set u.reply_count = u.reply_count + 1 " +
							"where d.id = ? ";
				preparedStatement = mConnection
							.prepareStatement(sql);
				preparedStatement.setInt(1, diaryId);
				preparedStatement.execute();*/
			}else{
				sql = "update t_kx_photos set comment_count = comment_count + 1 where id = ? ";
				preparedStatement = mConnection
						.prepareStatement(sql);
				preparedStatement.setInt(1, photoId);
				preparedStatement.execute();
			}
	
			sql = "update t_kx_users set gold = gold + ? where email = ?";
			preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setInt(1, 5);
			preparedStatement.setString(2, mUsername);
			preparedStatement.execute();
			response.getWriter().println("ok");
		}
		catch (Exception e)
		{
			response.getWriter().println(e.getMessage());
		}

	}

}
