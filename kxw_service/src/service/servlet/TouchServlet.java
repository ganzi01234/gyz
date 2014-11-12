package service.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TouchServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String friendEmail = request.getParameter("friendEmail");

		int touchCode = Integer.parseInt(request.getParameter("touchCode"));

		String sql = "insert into t_kx_touch(friend_email, my_email, touch_code,touch_time) values(?,?,?,?)";
		try
		{
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, friendEmail);
			preparedStatement.setString(2, mUsername);
			preparedStatement.setInt(3, touchCode);
			preparedStatement.setDate(4, new Date(new java.util.Date()
					.getTime()));

			preparedStatement.execute();

			response.getWriter().println("ok");
		}
		catch (Exception e)
		{
			response.getWriter().println(e.getMessage());
		}

	}

}
