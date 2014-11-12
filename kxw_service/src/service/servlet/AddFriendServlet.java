package service.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Diary;

public class AddFriendServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String friend_email = request.getParameter("friend_email");
		
		String sql = "insert into t_kx_friends(friend_email, my_email) values (?, ?) ";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, friend_email);
			preparedStatement.setString(2, mUsername);
			preparedStatement.execute();
			response.getWriter().println("ok");
		} catch (Exception e) {
			response.getWriter().println(e.getMessage());
		}
		
	}

}
