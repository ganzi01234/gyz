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
public class ModifyLocationServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String address = new String(request.getParameter("address").getBytes(
				"iso-8859-1"), "utf-8");
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude = Double.parseDouble(request.getParameter("longitude"));
		
		String sql = "update t_kx_users set location = ?, latitude = ?, longitude =? where email = ? ";
		try
		{
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, address);
			preparedStatement.setDouble(2, latitude);
			preparedStatement.setDouble(3, longitude);
			preparedStatement.setString(4, mUsername);
			preparedStatement.execute();
			
			sql = "insert into t_kx_location (email, time, location, latitude, longitude) values(?,?,?,?,?)";
			preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, mUsername);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			Date date = new Date();
			preparedStatement.setString(2, sdf.format(date));
			preparedStatement.setString(3, address);
			preparedStatement.setDouble(4, latitude);
			preparedStatement.setDouble(5, longitude);
			
			preparedStatement.execute();
			response.getWriter().println("ok");
		}
		catch (Exception e)
		{
			response.getWriter().println(e.getMessage());
		}

	}

}
