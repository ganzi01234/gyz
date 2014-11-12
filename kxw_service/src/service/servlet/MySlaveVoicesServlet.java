package service.servlet;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.VoiceResult;

public class MySlaveVoicesServlet extends CommonServlet
{
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			
			String email = request.getParameter("email");
			String sql = "select v.* from t_kx_voices v "+
						" where binary email= ? and state = 0 order by time desc"; 
			PreparedStatement preparedStatement = mConnection.prepareStatement(sql);
			preparedStatement.setString(1, email);

			ResultSet rs = preparedStatement.executeQuery();
	
			List<VoiceResult> slaves = new ArrayList<VoiceResult>();
			while(rs.next())
			{
				VoiceResult slave = new VoiceResult();
				slave.setName(rs.getString("email"));
				slave.setVid(rs.getString("id"));
				slave.setUrl(rs.getString("filename"));
				slave.setTime(rs.getString("time"));
				slave.setInfo(rs.getString("calling_number") + "-" + rs.getString("called_number"));
				slaves.add(slave);
			}
			sendJSON(slaves, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
