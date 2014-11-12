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


public class PhotoLikeServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		try
		{
			int photoId = Integer.parseInt(request.getParameter("photoId"));
			String sql = "update t_kx_photos set like_count = like_count + 1 where id = ? ";
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setInt(1, photoId);
			preparedStatement.execute();
			
//			sendObject(photoDetailResults, response);
			response.getWriter().println("³É¹¦");
			sql = "update t_kx_users set gold = gold + ? where email = ?";
			preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setInt(1, 5);
			preparedStatement.setString(2, mUsername);
			preparedStatement.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
