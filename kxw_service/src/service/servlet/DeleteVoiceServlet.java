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

public class DeleteVoiceServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		String vid = request.getParameter("id").toString();

		String sql = "delete from t_kx_voices v where v.id = ?";
		try
		{
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, vid);
			preparedStatement.execute();
	
			response.getWriter().println("ok");
		}
		catch (Exception e)
		{
			response.getWriter().println(e.getMessage());
		}

	}

}
