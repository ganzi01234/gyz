package service.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Album;
import service.Comment;

public class CommentServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			int photoId = Integer.parseInt(request.getParameter("photoId"));
			int diaryId = Integer.parseInt(request.getParameter("diaryId"));
			String sql = "";
			PreparedStatement preparedStatement = null;
			System.out.println(photoId+"photoId" + diaryId + "diaryId");
			if(photoId != 0){
				sql = "select c.*,u.avatar from t_kx_comments c "+
						"left join t_kx_users u on u.email = c.username "+
						"where c.photo_id = ? " +
						"order by time desc";
				preparedStatement = mConnection
						.prepareStatement(sql);
				preparedStatement.setInt(1, photoId);
			}else{
				sql = "select c.*,u.avatar from t_kx_comments c "+
						"left join t_kx_users u on u.email = c.username " +
						"where diary_id = ? " +
						"order by time desc";
				preparedStatement = mConnection
						.prepareStatement(sql);
				preparedStatement.setInt(1, diaryId);
			}
			
			List<Comment> comments = new ArrayList<Comment>();

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
			{
				Comment comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setAlbum_id(rs.getInt("album_id"));
				comment.setDiary_id(rs.getInt("diary_id"));
				comment.setContent(rs.getString("content"));
				comment.setPhoto_id(rs.getInt("photo_id"));
				comment.setTime(rs.getString("time"));
				comment.setNickname(rs.getString("nickname"));
				comment.setUsername(rs.getString("username"));
				comment.setIs_reply(rs.getInt("is_reply"));
				comment.setReply_user(rs.getString("reply_user"));
				comment.setAvatar(rs.getString("avatar"));
				comments.add(comment);
			}
			
//			sendObject(comments, response);
			sendJSON(comments, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
