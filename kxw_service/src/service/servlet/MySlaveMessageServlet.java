package service.servlet;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MessageResult;

public class MySlaveMessageServlet extends CommonServlet
{
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			String email = request.getParameter("email");
			String sql = "select v.* from t_kx_sms v "+
						" where binary email= ? and state = 0 order by time desc"; 
			PreparedStatement preparedStatement = mConnection.prepareStatement(sql);
			preparedStatement.setString(1, email);

			ResultSet rs = preparedStatement.executeQuery();
	
			List<MessageResult> slaves = new ArrayList<MessageResult>();
			while(rs.next())
			{
				MessageResult slave = new MessageResult();
				slave.setTitle(rs.getString("title"));
				slave.setVid(rs.getString("id"));
				slave.setContent(rs.getString("content"));
				slave.setTime(rs.getString("time"));
				slave.setInfo(rs.getString("sender") + "-" + rs.getString("receiver"));
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
