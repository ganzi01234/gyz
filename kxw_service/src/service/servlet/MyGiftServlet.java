package service.servlet;


import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import service.Gift;

public class MyGiftServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		
		String sql = "select * from t_kx_gift where friend_email=? order by gift_time desc";
		try
		{
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, mUsername);


			ResultSet rs = preparedStatement.executeQuery();
			List<Gift> gifts = new ArrayList<Gift>();
			while(rs.next())
			{
				Gift gift = new Gift();
				gift.setGiftCode(rs.getInt("gift_code"));
				gift.setPostscript(rs.getString("gift_postscript"));
				gifts.add(gift);
			}
			sendObject(gifts, response); 
		}
		catch (Exception e)
		{
			response.getWriter().println(e.getMessage());
		}

	}

}
