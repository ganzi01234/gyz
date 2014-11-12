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

public class AlbumServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		try
		{
			
			String uid = request.getParameter("uid");
			PreparedStatement preparedStatement = null;
			String sql = "";
			List<Album> albums = new ArrayList<Album>();
			if("".equals(uid)){
				sql = "select distinct(al.id),al.*,p.photo_filename from t_kx_albums al"+
								" left join t_kx_photos p on p.album_id = al.id" +
								" where email= ? " + 
								" group by al.id";
				preparedStatement = mConnection
						.prepareStatement(sql);
				preparedStatement.setString(1, mUsername);
				ResultSet rs = preparedStatement.executeQuery();
				if(!rs.next()){
					String create_sql = "insert into t_kx_albums(email, album_name, description) values(?,?,?)";
					preparedStatement = mConnection
							.prepareStatement(create_sql);
					String albumName = "ÊÖ»úÏà²á";
					String description = "ÊÖ»úÏà²á";

					preparedStatement.setString(1, mUsername);
					preparedStatement.setString(2, albumName);
					preparedStatement.setString(3, description);

					preparedStatement.execute();
					preparedStatement = mConnection
							.prepareStatement(sql);
					preparedStatement.setString(1, mUsername);
				}
			}else{
				sql = "select distinct(al.id), al.*, p.photo_filename from t_kx_users u"+
					        " left join t_kx_albums al ON al.email = u.email"+
					        " left join t_kx_photos p ON p.album_id = al.id"+
							" where u.id = ?"+
							" group by al.id";
				preparedStatement = mConnection
						.prepareStatement(sql);
				preparedStatement.setString(1, uid);
			}
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
			{
				Album album = new Album();
				album.setId(rs.getInt("id"));
				album.setName(rs.getString("album_name"));
				album.setEmail(rs.getString("email"));
				album.setDescription(rs.getString("description"));
				album.setImage(rs.getString("photo_filename"));
				albums.add(album);
			}

//			sendObject(albums, response);
			sendJSON(albums, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
