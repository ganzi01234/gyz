package service.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Diary;
import service.NearbyPeople;
import service.utils.LocationUtil;

public class NearByPeopleServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		List<NearbyPeople> peoples = new ArrayList<NearbyPeople>();
		try
		{
			double latitude = Double.parseDouble(request.getParameter("latitude"));
			double longitude = Double.parseDouble(request.getParameter("longitude"));
			PreparedStatement preparedStatement = null;
			String sql = "select u.*, RAND() as Random from t_kx_users u where "+
							"latitude > ? -1 and " +
							"latitude < ? +1 and " +
							"longitude > ? -1 and " +
							"longitude < ? +1 and " +
							"u.email <> ? " +
							"order by Random, ACOS(SIN((? * 3.1415) / 180 ) *SIN((latitude * 3.1415) / 180 ) +COS((? * 3.1415) / 180 ) * COS((latitude * 3.1415) / 180 ) *COS((? * 3.1415) / 180 - (longitude * 3.1415) / 180 ) ) * 6380 asc limit 10";
			preparedStatement = mConnection
						.prepareStatement(sql);
			preparedStatement.setDouble(1, latitude);
			preparedStatement.setDouble(2, latitude);
			preparedStatement.setDouble(3, longitude);
			preparedStatement.setDouble(4, longitude);
			preparedStatement.setString(5, mUsername);
			preparedStatement.setDouble(6, latitude);
			preparedStatement.setDouble(7, latitude);
			preparedStatement.setDouble(8, longitude);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next())
			{
				NearbyPeople people = new NearbyPeople();
				people.setUid(rs.getString("id"));
				people.setName(rs.getString("name"));
				people.setAvatar(rs.getString("avatar"));
				people.setEmail(rs.getString("email"));
				people.setLocation(rs.getString("location"));
				people.setLatitude(rs.getString("latitude"));
				people.setLongitude(rs.getString("longitude"));
				double distance = LocationUtil.getDistance(longitude, latitude, rs.getDouble("longitude"), rs.getDouble("latitude"));
				people.setDistance(distance);
				peoples.add(people);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		sendJSON(peoples, response);
//		sendObject(diaries, response);
	}

}