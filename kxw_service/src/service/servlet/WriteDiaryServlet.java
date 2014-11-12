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

public class WriteDiaryServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		String title = new String(request.getParameter("title").getBytes(
				"iso-8859-1"), "utf-8");
		
		String competencePosition = request.getParameter("competencePosition").toString();

		String content = new String(request.getParameter("content").getBytes(
				"iso-8859-1"), "utf-8");
		String filename = getRandomFileName();
		String path = mDiaryRootPath + mUsername.hashCode() + File.separator;

		File file = new File(path);
		if (!file.exists())
		{
			file.mkdirs();
		}
		String diaryFilename = path + filename;

		String sql = "insert into t_kx_diary(email, title, modify_date, filename, content, competence) values(?,?,?,?,?,?)";
		try
		{
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, mUsername);
			preparedStatement.setString(2, title);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			Date date = new Date();
			preparedStatement.setString(3, sdf.format(date));
			preparedStatement.setString(4, filename);
			preparedStatement.setString(5, content);
			preparedStatement.setString(6, competencePosition);
			preparedStatement.execute();
			
			sql = "update t_kx_users set gold = gold + ? where email = ?";
			preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setInt(1, 10);
			preparedStatement.setString(2, mUsername);
			preparedStatement.execute();
	
			FileOutputStream fos = new FileOutputStream(diaryFilename);
			fos.write(content.getBytes("utf-8"));
			fos.close();
			
			response.getWriter().println("ok");
			
			
		}
		catch (Exception e)
		{
			response.getWriter().println(e.getMessage());
		}

	}

}
