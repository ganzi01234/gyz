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
public class ModifySignatureServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String signature = new String(request.getParameter("signature").getBytes(
				"iso-8859-1"), "utf-8");
		
		String sql = "update t_kx_users set signature = ? where email = ? ";
		try
		{
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, signature);
			preparedStatement.setString(2, mUsername);
			preparedStatement.execute();
			
			response.getWriter().println("ok");
		}
		catch (Exception e)
		{
			response.getWriter().println(e.getMessage());
		}

	}

}
