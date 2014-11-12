package service.servlet;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.LocationResult;

public class MySlaveLocationServlet extends CommonServlet
{
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			String email = request.getParameter("email");
			String time = request.getParameter("time");
			System.out.println(email+time);
			String sql = "select u.* from t_kx_location u "+
						" where email= ? and time like ? "; 
			PreparedStatement preparedStatement = mConnection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, "%"+time+"%");

			ResultSet rs = preparedStatement.executeQuery();
			List<LocationResult> locations = new ArrayList<LocationResult>();
			while(rs.next())
			{
				LocationResult location = new LocationResult();
				location.setTime(rs.getString("time"));
				location.setLocation(rs.getString("location"));
				location.setLatitude(rs.getString("latitude"));
				location.setLongitude(rs.getString("longitude"));
				locations.add(location);
			}
			sendJSON(locations, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
