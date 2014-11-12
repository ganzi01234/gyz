package service.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DiaryServlet extends CommonServlet
{
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		try
		{
			int id = Integer.parseInt(request.getParameter("id"));

			String sql = "select filename from t_kx_diary where id=? ";
			PreparedStatement preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next())
			{
				String path = mDiaryRootPath + mUsername.hashCode()
						+ File.separator;

				String diaryFilename = path + rs.getString("filename");
				OutputStream os = response.getOutputStream();
				FileInputStream fis = new FileInputStream(diaryFilename);

				byte[] buffer = new byte[8192];
				int n = 0;
				while ((n = fis.read(buffer)) > 0)
				{
					os.write(buffer, 0, n);
				}

				fis.close();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
