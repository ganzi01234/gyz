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

@SuppressWarnings("serial")
public class ModifyUserInfoServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String birthday = request.getParameter("birthday");
		String mobile = request.getParameter("mobile");
		int gender = Integer.parseInt(request.getParameter("gender"));
		String nickname = new String(request.getParameter("nickname").getBytes(
				"iso-8859-1"), "utf-8");
		String address = request.getParameter("address");
		
		String sql = "update t_kx_users set birthday = ? , mobile = ? , sex_id = ? , name = ? , address = ? where email = ? ";
		try
		{
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, birthday);
			preparedStatement.setString(2, mobile);
			preparedStatement.setInt(3, gender);
			preparedStatement.setString(4, nickname);
			preparedStatement.setString(5, address);
			preparedStatement.setString(6, mUsername);
			preparedStatement.execute();
			
			response.getWriter().println("ok");
		}
		catch (Exception e)
		{
			response.getWriter().println(e.getMessage());
		}

	}

}
