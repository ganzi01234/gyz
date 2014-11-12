package service.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendGiftServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String friendEmail = request.getParameter("friendEmail");
		String postscript = new String(request.getParameter("postscript")
				.getBytes("iso-8859-1"), "utf-8");
		int giftCode = Integer.parseInt(request.getParameter("giftCode"));

		String sql = "insert into t_kx_gift(friend_email, my_email, gift_code,gift_time,gift_postscript, new_gift) values(?,?,?,?,?,?)";
		try
		{
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, friendEmail);
			preparedStatement.setString(2, mUsername);
			preparedStatement.setInt(3, giftCode);
			preparedStatement.setDate(4, new Date(new java.util.Date()
					.getTime()));
			preparedStatement.setString(5, postscript);
			preparedStatement.setInt(6, 1);
			preparedStatement.execute();

			response.getWriter().println("ok");
		}
		catch (Exception e)
		{
			response.getWriter().println(e.getMessage());
		}

	}

}
