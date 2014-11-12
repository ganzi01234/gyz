package service.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.PhotoDetailResult;


public class PhotoUrlServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		try
		{
			int page = Integer.parseInt(request.getParameter("page"));
			int albumId = Integer.parseInt(request.getParameter("albumId"));
			String sql = "select * from t_kx_photos where album_id = ? ";
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setInt(1, albumId);
			List<PhotoDetailResult> photoDetailResults = new ArrayList<PhotoDetailResult>();

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
			{
				PhotoDetailResult photoDetailResult = new  PhotoDetailResult();
				photoDetailResult.setId(rs.getInt("id"));
				photoDetailResult.setComment_count(rs.getInt("comment_count"));
				photoDetailResult.setLike_count(rs.getInt("like_count"));
				photoDetailResult.setTime(rs.getString("time"));
				photoDetailResult.setPhoto_filename(rs.getString("photo_filename"));
				photoDetailResult.setAlbum_id(rs.getInt("album_id"));
				photoDetailResults.add(photoDetailResult);
			}
			
//			sendObject(photoDetailResults, response);
			sendJSON(photoDetailResults, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
