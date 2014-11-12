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

public class FeedbackServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		String content = new String(request.getParameter("content").getBytes(
				"iso-8859-1"), "utf-8");

		String sql = "insert into t_kx_feedback(email, time, content) values(?,?,?)";
		try
		{
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, mUsername);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			Date date = new Date();
			preparedStatement.setString(2, sdf.format(date));
			preparedStatement.setString(3, content);
			preparedStatement.execute();
			response.getWriter().println("ok");
		}
		catch (Exception e)
		{
			response.getWriter().println(e.getMessage());
		}

	}

}
