package service.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Message;
import service.utils.MessageTimeComparator;
import service.utils.StringUtil;

public class MessagesServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String time = request.getParameter("time");
		List<Message> messages = new ArrayList<Message>();
		try
		{
			String sql = "";
			PreparedStatement preparedStatement = null;
			if(StringUtil.isNull(time)){
				sql = "select u.id as id, u.email as email, u.avatar as avatar, d.title as title, d.modify_date as time, u.name as name, '' as photo, d.comment_count as comment_count, d.like_count as like_count, 'viewed' as type, '' as wherefrom , '' as sign, d.id as messageid, '' as albumid "+ 
						"from t_kx_diary d " +
						"left join t_kx_users u ON u.email = d.email "+
						"where d.email = ? "+ 
						"or (d.email in(SELECT f.friend_email FROM t_kx_friends f where f.my_email = ?) and d.competence <> 2) "+
						"union all "+
						"select u.id as id, u.email as email, u.avatar as avatar, p.content as title, p.time as time, u.name as name, p.photo_filename as photo, p.comment_count as comment_count, p.like_count as like_count, 'photo' as type, '' as wherefrom, p.sign as sign, p.id as messageid, p.album_id as albumid "+ 
						"from t_kx_users u " +
						"inner join t_kx_photos p ON u.id = p.user_id " +
						"where u.email = ? " +
						"or (u.email in(SELECT f.friend_email FROM t_kx_friends f where f.my_email = ?) and p.competence <> 2) " +
						"union "+
						"select u.id as id, u.email as email, u.avatar as avatar, p.content as title, p.time as time, u.name as name, p.photo_filename as photo, p.comment_count as comment_count, p.like_count as like_count, 'photo' as type, '' as wherefrom, p.sign as sign, p.id as messageid, p.album_id as albumid "+ 
						"from t_kx_users u " +
						"inner join t_kx_photos p ON u.id = p.user_id " +
						"where p.competence = 0 "+
						"union "+
						"select u.id as id, u.email as email, u.avatar as avatar, d.title as title, d.modify_date as time, u.name as name, '' as photo, d.comment_count as comment_count, d.like_count as like_count, 'viewed' as type, '' as wherefrom , '' as sign, d.id as messageid, '' as albumid "+ 
						"from t_kx_diary d " +
						"left join t_kx_users u ON u.email = d.email " +
						"where d.competence = 0";
			}else{
				sql = "select u.id as id, u.email as email, u.avatar as avatar, d.title as title, d.modify_date as time, u.name as name, '' as photo, d.comment_count as comment_count, d.like_count as like_count, 'viewed' as type, '' as wherefrom , '' as sign, d.id as messageid, '' as albumid "+ 
						"from t_kx_diary d " +
						"left join t_kx_users u ON u.email = d.email "+
						"where (d.email = ? and d.modify_date > '"+ time +"') " +
						"or (d.competence <> 2 and (d.email in(SELECT f.friend_email FROM t_kx_friends f where f.my_email = ?) and d.modify_date > '"+ time +"')) " +
						"union all "+
						"select u.id as id, u.email as email, u.avatar as avatar, p.content as title, p.time as time, u.name as name, p.photo_filename as photo, p.comment_count as comment_count, p.like_count as like_count, 'photo' as type, '' as wherefrom, p.sign as sign, p.id as messageid, p.album_id as albumid "+ 
						"from t_kx_users u " +
						"inner join t_kx_photos p ON u.id = p.user_id " +
						"where (u.email = ? and p.time > '"+ time +"') " +
						"or (p.competence <> 2 and (u.email in(SELECT f.friend_email FROM t_kx_friends f where f.my_email = ?) and p.time > '"+ time +"')) " +
						"union "+
						"select u.id as id, u.email as email, u.avatar as avatar, p.content as title, p.time as time, u.name as name, p.photo_filename as photo, p.comment_count as comment_count, p.like_count as like_count, 'photo' as type, '' as wherefrom, p.sign as sign, p.id as messageid, p.album_id as albumid "+ 
						"from t_kx_users u " +
						"inner join t_kx_photos p ON u.id = p.user_id " +
						"where p.time > '"+ time +"' and p.competence = 0 "+
						"union "+
						"select u.id as id, u.email as email, u.avatar as avatar, d.title as title, d.modify_date as time, u.name as name, '' as photo, d.comment_count as comment_count, d.like_count as like_count, 'viewed' as type, '' as wherefrom , '' as sign, d.id as messageid, '' as albumid "+ 
						"from t_kx_diary d " +
						"left join t_kx_users u ON u.email = d.email " +
						"where d.modify_date > '"+ time +"' and d.competence = 0";
			}
			
			preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, mUsername);
			preparedStatement.setString(2, mUsername);
			preparedStatement.setString(3, mUsername);
			preparedStatement.setString(4, mUsername);

			ResultSet rs = preparedStatement.executeQuery();
			String sign = "";
			while (rs.next())
			{
				if("photo".equals(rs.getString("type")) && sign != null && sign.equals(rs.getString("sign"))){
					List<String> photos = messages.get(messages.size() - 1).getPhoto();
					photos.add(rs.getString("photo"));
					messages.get(messages.size() - 1).setPhoto(photos);
					messages.get(messages.size() - 1).setComment_count(messages.get(messages.size() - 1).getComment_count() + rs.getInt("comment_count"));
					messages.get(messages.size() - 1).setLike_count(messages.get(messages.size() - 1).getLike_count() + rs.getInt("like_count"));
				}else{
					Message message = new Message();
					message.setUid(rs.getString("id"));
					message.setEmail(rs.getString("email"));
					message.setTitle(rs.getString("title"));
					message.setTime(rs.getString("time"));
					message.setName(rs.getString("name"));
					List<String> photos = new LinkedList<String>();
					photos.add(rs.getString("photo"));
					message.setPhoto(photos);
					message.setComment_count(rs.getInt("comment_count"));
					message.setLike_count(rs.getInt("like_count"));
					message.setAvatar(rs.getString("avatar"));
					message.setType(rs.getString("type"));
					message.setFrom(rs.getString("wherefrom"));
					message.setMessageid(rs.getString("messageid"));
					message.setAlbumid(rs.getInt("albumid"));
					messages.add(message);
					if("photo".equals(rs.getString("type"))){
						sign = rs.getString("sign");
					}
				}
			}
			Collections.sort(messages, Collections.reverseOrder(new MessageTimeComparator()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		sendJSON(messages, response);
//		sendObject(diaries, response);
	}

}
