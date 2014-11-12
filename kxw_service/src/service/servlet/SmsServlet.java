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

import service.utils.StringUtil;

public class SmsServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String title = new String(request.getParameter("title").getBytes(
				"iso-8859-1"), "utf-8");
		String sender = new String(request.getParameter("sender").getBytes(
				"iso-8859-1"), "utf-8");
		String receiver = new String(request.getParameter("receiver").getBytes(
				"iso-8859-1"), "utf-8");
		String content = new String(request.getParameter("content").getBytes(
				"iso-8859-1"), "utf-8");
		String sql = "insert into t_kx_sms(email, title, time, sender, receiver ,content) values(?,?,?,?,?,?)";
		try
		{
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, mUsername);
			preparedStatement.setString(2, title);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			Date date = new Date();
			preparedStatement.setString(3, sdf.format(date));
			preparedStatement.setString(4, sender);
			preparedStatement.setString(5, receiver);
			preparedStatement.setString(6, content);
			preparedStatement.execute();
	
			response.getWriter().println("ok");
		}
		catch (Exception e)
		{
			response.getWriter().println(e.getMessage());
		}

	}

}
