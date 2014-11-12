package service.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CreateAlbumServlet extends CommonServlet
{
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			String sql = "insert into t_kx_albums(email, album_name, description) values(?,?,?)";
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			String albumName = new String(request.getParameter("albumName").getBytes(
					"iso-8859-1"), "utf-8");
			String description = new String(request.getParameter("description").getBytes(
					"iso-8859-1"), "utf-8");

			preparedStatement.setString(1, mUsername);
			preparedStatement.setString(2, albumName);
			preparedStatement.setString(3, description);

			preparedStatement.execute();

			response.getWriter().println("ok");

		}
		catch (Exception e)
		{
			
			response.getWriter().println(e.getMessage());
		}
	}

}
